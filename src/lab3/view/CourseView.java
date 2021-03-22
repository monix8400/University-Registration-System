package lab3.view;

import lab3.model.Course;
import lab3.model.Student;

public class CourseView {
    public void printCourseDetails(Course c){
        System.out.println("Course: ");
        System.out.println("Name: " + c.getName());
        System.out.println("Teacher: "+c.getTeacher().getFirstName() + " " + c.getTeacher().getLastName());
        System.out.println("Maximal Enrollment: "+c.getMaxEnrollment());
        System.out.println("Total credits: " + c.getCredits());
        System.out.println("Students enrolled: " + c.getStudentsEnrolled().size());
        System.out.println("Enrolled students");
        for (Student s:c.getStudentsEnrolled()) {
            System.out.println(s.getFirstName() + " " + s.getLastName());
        }
        System.out.println();
    }
}
