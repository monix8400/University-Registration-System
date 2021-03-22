package lab3.model;

import java.util.List;

public class Teacher extends Person {
    private List<Course> courses;
    long teacherID;

    public Teacher(List<Course> courses,long teacherID, String firstName, String lastName) {
        super(firstName, lastName);
        this.courses = courses;
        this.teacherID=teacherID;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public long getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(long teacherID) {
        this.teacherID = teacherID;
    }
}
