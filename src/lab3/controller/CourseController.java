package lab3.controller;

import lab3.model.Course;
import lab3.model.Person;
import lab3.model.Student;
import lab3.view.CourseView;

import java.util.List;

public class CourseController {

    private final Course model;
    private final CourseView view;

    public CourseController(Course model, CourseView view) {
        this.model = model;
        this.view = view;
    }

    public int getTotalCredits() {
        return model.getCredits();
    }

    public void setTotalCredits(int credits) {
        model.setCredits(credits);
    }

    public void setCourseName(String name){
        model.setName(name);
    }

    public String getCourseName(){
        return model.getName();
    }

    public void setTeacher(Person teacher){model.setTeacher(teacher);}

    public Person getTeacher() {
        return model.getTeacher();
    }

    public void setStudentsEnrolled(List<Student> courses) {
        model.setStudentsEnrolled(courses);
    }

    public List<Student> getStudentsEnrolled() {
        return model.getStudentsEnrolled();
    }

    public void setMaxEnrollment (int maxEnrollment){ model.setMaxEnrollment(maxEnrollment);}

    public int getMaxEnrollment (){return model.getMaxEnrollment();}

    public void showView(){view.printCourseDetails(model);}
}
