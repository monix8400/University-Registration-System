package lab3.controller;

import lab3.model.Course;
import lab3.model.Student;
import lab3.view.StudentView;

import java.util.List;

public class StudentController {
    private final Student model;
    private final StudentView view;

    public StudentController(Student model, StudentView view){
        this.model = model;
        this.view = view;
    }

    public void setStudentFirstName(String name){
        model.setFirstName(name);
    }

    public void setStudentLastName(String name){
        model.setLastName(name);
    }

    public String getStudentFirstName(){
        return model.getFirstName();
    }

    public String getStudentLastName(){
        return model.getLastName();
    }

    public int getStudentTotalCredits() {
        return model.getTotalCredits();
    }

    public void setStudentTotalCredits(int totalCredits){
        model.setTotalCredits(totalCredits);
    }

    public List<Course> getStudentEnrolledCourses() {
        return model.getEnrolledCourses();
    }

    public void setStudentEnrolledCourses(List<Course> courses) {
        model.setEnrolledCourses(courses);
    }

    public void showView(){
        view.printStudentDetails(model);
    }
}