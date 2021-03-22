package lab3.javafx.controllers;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lab3.javafx.Main;
import lab3.model.Teacher;

import java.io.IOException;

public class StartTeacher {
    public TextField firstName;
    public TextField lastName;
    public Text errorField;

    public void loginTeacher() throws IOException {
        // checking if a corresponding teacher with firstName and lastName exists
        Teacher teacher = Main.getTeacherRepo().findTeacherByName(firstName.getText(), lastName.getText());
//        Teacher teacher = Main.getTeacherRepo().findTeacherByName("Diana", "Troanca");

        // credentials are wrong
        if (teacher == null) {
            errorField.setText("Teacher does not exist. Please try again.");
            firstName.setText("");
            lastName.setText("");
        }
        // no errors
        else {
            Start.setActiveTeacher(teacher);
            Start.changeScene("../screens/TeacherActions.fxml", Start.getTeacherStage());
        }
    }
}
