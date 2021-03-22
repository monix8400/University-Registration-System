package lab3.controller;

import lab3.model.Course;
import lab3.model.Teacher;
import lab3.view.TeacherView;

import java.util.List;

public class TeacherController {
    private final Teacher model;
    private final TeacherView view;

    public TeacherController(Teacher model, TeacherView view) {
        this.model = model;
        this.view = view;
    }

    public void setTeacherFirstName(String name){
        model.setFirstName(name);
    }

    public void setTeacherLastName(String name){
        model.setLastName(name);
    }

    public String getTeacherFirstName(){
        return model.getFirstName();
    }

    public String getTeacherLastName(){
        return model.getLastName();
    }

    public List<Course> getTeacherCourses() {
        return model.getCourses();
    }

    public void setTeacherCourses(List<Course> courses) {
        model.setCourses(courses);
    }

    public void showView(){view.printTeacherDetails(model);}
}
