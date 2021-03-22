package lab3.view;
import lab3.model.Course;
import lab3.model.Student;

public class StudentView {
    public void printStudentDetails(Student s){
        System.out.println("\nStudent: ");
        System.out.println("Name: " + s.getFirstName() + " " + s.getLastName());
        System.out.println("Total credits: " + s.getTotalCredits());
        System.out.println("Enrolled courses");
        for (Course c:s.getEnrolledCourses()) {
            System.out.println(c.getName());
        }
    }
}