package lab3.javafx.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lab3.javafx.Main;
import lab3.model.Course;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentActions implements Initializable {
    public Text activeStudent;
    public Text credits;

    @FXML
    public VBox vbox;
    @FXML
    public Button logOutBtnS;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        activeStudent.setText(Start.getActiveStudent().getFirstName() + " " + Start.getActiveStudent().getLastName());
        credits.setText(Start.getActiveStudent().getTotalCredits() + "");

        // showing available courses
        for (Course c : Main.getRegSys().retrieveCoursesWithFreePlaces()) {
            VBox newCourse = new VBox();
            HBox hBox=new HBox();
            Text newText1 = new Text();
            Text newText2 = new Text();
            Text newError = new Text();

            // setting up enroll button
            Button newButton = new Button();
            newButton.setText("Enroll");
            newButton.setStyle("-fx-background-color: white;");

            // user clicks on ENROLL
            newButton.setOnAction(event -> {
                // enrollment good
                if (Main.getRegSys().register(c, Start.getActiveStudent())) {
                    newButton.setText("Enrollment succesfully");
                    credits.setText(Start.getActiveStudent().getTotalCredits()+"");

                    // setting state for observer
                    if (Start.getActiveTeacher() != null)
                        Main.getSubject().setState((List<Course>) Main.getCourseRepo().findAll());
                }
                // error
                else {
                    newButton.setText("Enroll");
                    newError.setText("Student already enrolled or no more free places, please choose another course.");
                }
            });

            // setting details of course
            String availableCoursesName = c.getName();
            String availableCourses =
                    "Teacher: " + c.getTeacher().getFirstName() + " " + c.getTeacher().getLastName() + "\n" +
                    "Free places: " + (c.getMaxEnrollment() - c.getStudentsEnrolled().size()) + "\n" +
                    "Credits: " + c.getCredits() + "\n\n";
            newText1.setText(availableCoursesName);
            newText1.setFont(Font.font("DejaVu Serif Bold",18));
            newText1.setFill(Color.WHITE);

            newText2.setText(availableCourses);

            // adding elements to scene
            hBox.getChildren().addAll(newText1,newText2,newButton);
            HBox.setMargin(newText1,new Insets(15,50,0,0));
            HBox.setMargin(newButton,new Insets(15,0,0,100));
            newCourse.getChildren().addAll(hBox,newError);
            VBox.setMargin(newError,new Insets(0,0,30,0));
            vbox.getChildren().add(newCourse);
            VBox.setMargin(newCourse, new Insets(0,0,0,30));
        }
    }

    public void logOutBtnStudent(ActionEvent event){
        Stage stage = (Stage) logOutBtnS.getScene().getWindow();
        stage.close();
    }
}
