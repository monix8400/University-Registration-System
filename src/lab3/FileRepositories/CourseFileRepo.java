package lab3.FileRepositories;

import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;
import lab3.repository.CourseRepository;
import lab3.repository.StudentRepository;
import lab3.repository.TeacherRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CourseFileRepo implements FileRepository<Course> {
    private final String filename;
    StudentRepository studentRepo;
    TeacherRepository teacherRepo;

    /**
     *
     * @param filename
     * @param studentRepo
     * @param teacherRepo
     */
    public CourseFileRepo(String filename, StudentRepository studentRepo, TeacherRepository teacherRepo) {
        this.filename = filename;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
    }

    /**
     * @returns the desired E type list
     */
    @Override
    public List<Course> getObjects() {
        JSONArray coursesList = read(filename);
        List<Course> courses = new ArrayList<>();

        for (Object obj :coursesList) {
            courses.add(parseObject((JSONObject) obj));
        }

        return courses;
    }

    /**
     * function to clear json file
     */
    @Override
    public void clear() {
        JSONArray empty = new JSONArray();
        try (FileWriter file = new FileWriter("src/lab3/database/" + filename)) {
            file.write(empty.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param obj is new entity to be written in json file
     */
    @SuppressWarnings("unchecked")
    @Override
    public void add(Course obj) {
        // New object
        JSONObject objectDetails = new JSONObject();
        objectDetails.put("id", obj.getID());
        objectDetails.put("name", obj.getName());
        objectDetails.put("teacher", obj.getTeacher().getFirstName() + ' ' + obj.getTeacher().getLastName());
        objectDetails.put("maxEnrollment", obj.getMaxEnrollment());
        objectDetails.put("credits", obj.getCredits());

        JSONObject courseObject = new JSONObject();
        courseObject.put("course", objectDetails);

        JSONArray coursesList = read(filename);
        coursesList.add(courseObject);

        //Write JSON file
        try (FileWriter file = new FileWriter("src/lab3/database/" + filename)) {
            file.write(coursesList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param obj jsonobject from which to parse stuff and then form the desired E entity
     * @return
     */
    @Override
    public Course parseObject(JSONObject obj) {
        //Get object within list
        JSONObject courseObj = (JSONObject) obj.get("course");

        String name = (String) courseObj.get("name");
        long id = (Long) courseObj.get("id");
        long credits = (Long) courseObj.get("credits");
        long maxEnrollment = (Long) courseObj.get("maxEnrollment");

        List<Student> studentsEnrolled = new ArrayList<>();

        for (Teacher t: teacherRepo.findAll()) {
            String tName = t.getFirstName() + " " + t.getLastName();
            if (tName.equals(courseObj.get("teacher"))) {
                Course newCourse = new Course(id,name,t,(int) maxEnrollment,studentsEnrolled,(int) credits);

                List<Course> teacherCourses = t.getCourses();
                teacherCourses.add(newCourse);
                t.setCourses(teacherCourses);

                return newCourse;
            }
        }

        return null;
    }

    /**
     * function to bind data to each student, teacher, courses such as enrolledStudents, studentsEnrolled at a specific course
     * and courses held by a specific teacher, does this by having information from the json files
     * @param courseRepo
     */
    public void setRelations(CourseRepository courseRepo) {
        JSONArray students = read("students.json");

        for (Object obj :students) {
            JSONObject object = (JSONObject) obj;
            JSONObject student = (JSONObject) object.get("student");

            long id = (Long) student.get("id");
            JSONArray coursesIDs = (JSONArray) student.get("enrolledCourses");

            List<Course> enrolledCourses = new ArrayList<>();
            for (Object nr:coursesIDs) {
                long courseId = (long) nr;

                // setting students enrolled for the course
                enrolledCourses.add(courseRepo.findOne(courseId));

                List<Student> studentsEnrolled = courseRepo.findOne(courseId).getStudentsEnrolled();
                studentsEnrolled.add(studentRepo.findOne(id));
            }

            // setting courses enrolled for student
            studentRepo.findOne(id).setEnrolledCourses(enrolledCourses);
        }
    }
}
