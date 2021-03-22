package lab3.javafx.controllers;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lab3.javafx.Main;
import lab3.model.Student;

import java.io.IOException;

public class StartStudent {
    public TextField firstName;
    public TextField lastName;
    public Text errorField;

    public void loginStudent() throws IOException {
        // checking if a corresponding student with firstName and lastName exists
        Student student = Main.getStudentRepo().findStudentByName(firstName.getText(), lastName.getText());
//        Student student = Main.getStudentRepo().findStudentByName("elon", "musk");

        // credentials are wrong
        if (student == null) {
            errorField.setText("Student does not exist. Please try again.");
            firstName.setText("");
            lastName.setText("");
        }
        // no errors
        else {
            Start.setActiveStudent(student);
            Start.changeScene("../screens/StudentActions.fxml", Start.getStudentStage());
        }
    }
}
