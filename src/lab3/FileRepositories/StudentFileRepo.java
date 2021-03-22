package lab3.FileRepositories;
import lab3.model.Course;
import lab3.model.Student;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentFileRepo implements FileRepository<Student> {
    private final String filename;

    public StudentFileRepo(String filename) {
        this.filename = filename;
    }

    /**
     *
     * @return the desired list of objects
     */
    @Override
    public List<Student> getObjects() {
        JSONArray list = read(filename);
        List<Student> students = new ArrayList<>();

        for (Object obj :list) {
            students.add(parseObject((JSONObject) obj));
        }

        return students;
    }

    /**
     *
     * @param obj is new entity to be written in json file
     */
    @SuppressWarnings("unchecked")
    @Override
    public void add(Student obj) {
        // New object
        JSONObject objectDetails = new JSONObject();
        objectDetails.put("id", obj.getStudentID());
        objectDetails.put("firstName", obj.getFirstName());
        objectDetails.put("lastName", obj.getLastName());
        objectDetails.put("totalCredits", obj.getTotalCredits());

        List<Long> courses = new ArrayList<>();
        for (Course c:obj.getEnrolledCourses()) {
            courses.add(c.getID());
        }

        objectDetails.put("enrolledCourses", courses);

        JSONObject studentObject = new JSONObject();
        studentObject.put("student", objectDetails);

        JSONArray studentsList = read(filename);
        studentsList.add(studentObject);

        //Write JSON file
        try (FileWriter file = new FileWriter("src/lab3/database/" + filename)) {

            file.write(studentsList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * empties the json file
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
     * @param student
     * @returns formed object
     */
    public Student parseObject(JSONObject student)
    {
        //Get object within list
        JSONObject studentObject = (JSONObject) student.get("student");

        String firstName = (String) studentObject.get("firstName");
        String lastName = (String) studentObject.get("lastName");
        long id = (Long) studentObject.get("id");
        long totalCredits = (Long) studentObject.get("totalCredits");
        List<Course> courses = new ArrayList<>();

        return new Student(firstName,lastName,id,(int) totalCredits,courses);
    }
}
