package lab3;

import lab3.controller.CourseController;
import lab3.model.Course;
import lab3.model.Student;
import lab3.repository.CourseRepository;
import lab3.view.CourseView;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RegistrationSystem {

    private final CourseRepository courseRepo;

    /**
     * constructor
     * @param courseRepo initializer
     */
    public RegistrationSystem(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    /**
     * register a student
     * @param c course,
     * @param s student,
     * @return true, if students register, false otherwise
     */
    public boolean register(Course c, Student s) {
        // no free places
        if (c.getStudentsEnrolled().size() >= c.getMaxEnrollment())
            return false;
        // cannot have more than 30 credits
        else if (s.getTotalCredits() + c.getCredits() > 30)
            return false;

        // REFACTORED
        // checking if student is already registered
        boolean exists = c.getStudentsEnrolled().stream()
                .anyMatch(student -> student.getStudentID() == s.getStudentID());

        // if lambda returned true, student is already present
        if (exists)
            return false;

        // if none of the above set the student to the courses enrolledStudents
        c.getStudentsEnrolled().add(s);
        c.setStudentsEnrolled(c.getStudentsEnrolled());

        // updating number of credits
        s.setTotalCredits(s.getTotalCredits() + c.getCredits());

        // setting the new course to the students courses list
        List<Course> studentCourses = s.getEnrolledCourses();
        studentCourses.add(c);
        s.setEnrolledCourses(studentCourses);

        return true;
    }

    /**
     * searches the courses with free places
     * @return coursesWithFreePlaces
     */
    public List<Course> retrieveCoursesWithFreePlaces() {
        // REFACTORED
        // used StreamSupport to convert Iterable to stream
        return StreamSupport.stream(courseRepo.findAll().spliterator(), false)
                .filter(c -> c.getStudentsEnrolled().size() < c.getMaxEnrollment())
                .collect(Collectors.toList());
    }

    /**
     * search for the students enrolled for a course
     * @param c course
     * @return the students enrolled for a course, otherwise null
     */
    public List<Student> retrieveStudentsEnrolledForACourse(Course c) {

        // REFACTORED
        return getAllCourses().stream()
                .filter(course -> course.getID() == c.getID())
                .map(Course::getStudentsEnrolled)
                .findAny()
                .orElse(null);
    }

    /**
     * shows a list of details of a course
     * @param c course
     */
    public void printSpecificCourseDetails(Course c) {
        CourseView view = new CourseView();
        CourseController controller = new CourseController(c, view);
        controller.showView();
    }

    /**
     * retrieves all courses
     * @return all courses
     */
    public List<Course> getAllCourses() {
        return (List<Course>) courseRepo.findAll();
    }
}
