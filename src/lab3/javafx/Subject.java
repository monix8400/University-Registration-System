package lab3.javafx;

import lab3.model.Course;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    private final List<Observer> observers = new ArrayList<>();
    private List<Course> courses;

    /**
     * getting current state of the course array
     * @return current courses
     */
    public List<Course> getState() {
        return courses;
    }

    /**
     * modifying courses array
     * @param courses new courses
     */
    public void setState(List<Course> courses) {
        this.courses = courses;
        notifyAllObservers();
    }

    /**
     * adding listeners to state change
     * @param observer to be listening
     */
    public void attach(Observer observer){
        observers.add(observer);
    }

    /**
     * on update we notify all observers
     */
    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }
}