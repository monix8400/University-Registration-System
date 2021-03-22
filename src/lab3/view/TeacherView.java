package lab3.view;

import lab3.model.Course;
import lab3.model.Teacher;

public class TeacherView {
    public void printTeacherDetails(Teacher t){
        System.out.println("Teacher: ");
        System.out.println("Name: " + t.getFirstName() + " " + t.getLastName());
        System.out.println("Courses: ");
        for (Course c:t.getCourses()) {
            System.out.println(c.getName());
        }
    }
}
