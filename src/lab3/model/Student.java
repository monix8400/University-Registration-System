package lab3.model;

import java.util.List;

public class Student extends Person {
    private long studentID;
    private int totalCredits;
    private List<Course> enrolledCourses;

    public Student(String firstName, String lastName, long studentID,int totalCredits, List<Course> enrolledCourses) {
        super(firstName, lastName);
        this.studentID = studentID;
        this.totalCredits=totalCredits;
        this.enrolledCourses=enrolledCourses;
    }

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }
}
