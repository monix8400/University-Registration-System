package lab3.javafx;

import lab3.javafx.controllers.TeacherActions;

public class CoursesObserver extends Observer{

    public CoursesObserver(Subject subject){
        this.subject = subject;
        this.subject.attach(this);
    }

    /**
     * this function will run each time one course will be modified
     * ex. one student enrolls to a new course, teacher updates course
     */
    @Override
    public void update() {
        // calls a method to reset courses in TeacherActions
        TeacherActions.updateCourses(subject.getState());
    }
}
