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
import lab3.model.Course;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TeacherActions implements Initializable {
    @FXML
    private VBox vbox;
    public static VBox static_vbox;

    @FXML
    private Text activeTeacher;
    public static Text static_activeTeacher;

    @FXML
    public Button logOutBtnT;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // initializing static variables in order to gain acces from static functions
        static_vbox = vbox;
        static_activeTeacher = activeTeacher;

        // setting name of active teacher
        static_activeTeacher.setText(Start.getActiveTeacher().getFirstName() + " " + Start.getActiveTeacher().getLastName());

        // showing courses on the screen
        showCourses(Start.getActiveTeacher().getCourses());
    }

    /**
     * function that is triggered by the observer whenever someone enrolls
     * @param courses new state that has been updated by a student
     */
    public static void updateCourses(List<Course> courses) {

        // getting list of courses of the active teacher
        List<Course> newCourses = courses.stream()
            .filter(c -> c.getTeacher() == Start.getActiveTeacher())
            .collect(Collectors.toList());

        // clearing old courses from the vbox
        static_vbox.getChildren().clear();

        // showing new courses
        showCourses(newCourses);
    }

    /**
     * function to show courses on the teacher screen
     * @param courses to be shown
     */
    public static void showCourses(List<Course> courses) {

        // showing available courses
        for (Course c : courses) {
            VBox newCourse = new VBox();
            HBox hBox=new HBox();
            Text newText1 = new Text();
            Text newText2 = new Text();

            // getting a string with all enrolled students to course
            String enrolledStudents = c.getStudentsEnrolled().stream()
                    .map(s -> s.getFirstName() + " " + s.getLastName())
                    .collect(Collectors.joining(", ","",""));

            // setting details of course
            String availableCoursesName = c.getName();
            String availableCourses =
                    "Free places: " + (c.getMaxEnrollment() - c.getStudentsEnrolled().size()) + "\n" +
                    "Credits: " + c.getCredits() + "\n" +
                    "Enrolled students: " + enrolledStudents + "\n\n";
            newText1.setText(availableCoursesName);
            newText1.setFont(Font.font("DejaVu Serif Bold",18));
            newText1.setFill(Color.WHITE);

            newText2.setText(availableCourses);

            // adding elements to scene
            hBox.getChildren().addAll(newText1,newText2);
            HBox.setMargin(newText1,new Insets(15,50,0,0));
            newCourse.getChildren().add(hBox);
            static_vbox.getChildren().add(newCourse);
            VBox.setMargin(newCourse, new Insets(0,0,0,30));
        }
    }

    public void logOutBtnTeacher(ActionEvent event){
        Stage stage = (Stage) logOutBtnT.getScene().getWindow();
        stage.close();
    }
}
