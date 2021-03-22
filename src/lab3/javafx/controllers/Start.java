package lab3.javafx.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab3.model.Student;
import lab3.model.Teacher;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Start implements Initializable {

    // 2 different stages for teacher- and student options
    public static Stage studentStage;
    public static Stage teacherStage;

    // active users
    public static Teacher activeTeacher;
    public static Student activeStudent;

    /** initializing window
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void openStartStudent() throws IOException {
        Stage newStage = new Stage();
        Parent root1;
        root1 = FXMLLoader.load(getClass().getResource("../screens/StartStudent.fxml"));

        setStudentStage(newStage);

        newStage.setScene(new Scene(root1));
        newStage.setTitle("L6");
        newStage.show();
    }

    public void openStartTeacher() throws IOException {
        Stage newStage = new Stage();
        Parent root1;
        root1 = FXMLLoader.load(getClass().getResource("../screens/StartTeacher.fxml"));

        setTeacherStage(newStage);

        newStage.setScene(new Scene(root1));
        newStage.setTitle("L6");
        newStage.show();
    }

    /**
     * function to switch between scenes
     */
    public static void changeScene(String filename, Stage s) throws IOException {
        Parent pane = FXMLLoader.load(Start.class.getResource(filename));
        s.getScene().setRoot(pane);
    }

    // GETTERS AND SETTERS

    public static Stage getTeacherStage() {
        return teacherStage;
    }

    public static void setTeacherStage(Stage teacherStage) {
        Start.teacherStage = teacherStage;
    }

    public static Teacher getActiveTeacher() {
        return activeTeacher;
    }

    public static void setActiveTeacher(Teacher activeTeacher) {
        Start.activeTeacher = activeTeacher;
    }

    public static Stage getStudentStage() {
        return studentStage;
    }

    public static void setStudentStage(Stage studentStage) {
        Start.studentStage = studentStage;
    }

    public static Student getActiveStudent() {
        return activeStudent;
    }

    public static void setActiveStudent(Student activeStudent) {
        Start.activeStudent = activeStudent;
    }

}
