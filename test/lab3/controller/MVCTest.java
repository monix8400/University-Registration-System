package lab3.controller;

import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;
import lab3.view.CourseView;
import lab3.view.StudentView;
import lab3.view.TeacherView;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MVCTest {
    @Test
    public void testStudent() {
        //fetch student record based on his roll no from the database
        Student model  = retriveStudent();

        //Create a view : to write student details on console
        StudentView view = new StudentView();

        StudentController controller = new StudentController(model, view);

        controller.showView();

        assert controller.getStudentFirstName().equals("Robert");
        assert controller.getStudentLastName().equals("Downey Jr");
        assert controller.getStudentTotalCredits() == 10;

        //update model data
        controller.setStudentFirstName("John");
        controller.setStudentLastName("Cena");

        assert controller.getStudentFirstName().equals("John");
        assert controller.getStudentLastName().equals("Cena");
        assert controller.getStudentTotalCredits() == 10;
        controller.showView();
    }

    @Test
    public void testCourse() {
        //fetch course record based on his roll no from the database
        Course model  = retrieveCourse();

        //Create a view : to write student details on console
        CourseView view = new CourseView();

        CourseController controller = new CourseController(model, view);

        controller.showView();
        assert controller.getCourseName().equals("BD");
        assert controller.getTeacher().getFirstName().equals("Diana");
        assert controller.getMaxEnrollment() == 60;
        assert controller.getTotalCredits() == 6;

        //update model data
        controller.setCourseName("DB2");
        controller.setMaxEnrollment(30);

        assert controller.getCourseName().equals("DB2");
        assert controller.getMaxEnrollment() == 30;

        controller.showView();
    }

    @Test
    public void testTeacher() {
        //fetch teacher record based on his roll no from the database
        Teacher model  = retriveTeacher();

        //Create a view : to write student details on console
        TeacherView view = new TeacherView();

        TeacherController controller = new TeacherController(model, view);

        controller.showView();
        assert controller.getTeacherFirstName().equals("Diana");
        assert controller.getTeacherLastName().equals("Cristea");

        //update model data
        controller.setTeacherLastName("Troanca");
        assert controller.getTeacherLastName().equals("Troanca");

        controller.showView();
    }

    private static Student retriveStudent(){
        List<Course> coursesEnrolledS1 = new ArrayList<>();
        Student s1 = new Student("elon","musk",1,12,coursesEnrolledS1);
        s1.setFirstName("Robert");
        s1.setLastName("Downey Jr");
        s1.setTotalCredits(10);
        return s1;
    }

    private static Course retrieveCourse(){
        List<Course> coursesByT1 = new ArrayList<>();
        List<Student> studentEnrolledBD = new ArrayList<>();
        Teacher t1 = new Teacher(coursesByT1,1, "Diana","Troanca");
        Course c1 = new Course(1,"BD",t1,60,studentEnrolledBD,6);
        t1.setLastName("Cristea");

        return c1;
    }

    private static Teacher retriveTeacher(){
        List<Course> coursesByT1 = new ArrayList<>();
        Teacher t1 = new Teacher(coursesByT1,1, "Diana","Troanca");
        t1.setLastName("Cristea");

        return t1;
    }
}
