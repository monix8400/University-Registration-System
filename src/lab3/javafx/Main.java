package lab3.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lab3.FileRepositories.CourseFileRepo;
import lab3.FileRepositories.FileRepository;
import lab3.FileRepositories.StudentFileRepo;
import lab3.FileRepositories.TeacherFileRepo;
import lab3.RegistrationSystem;
import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;
import lab3.repository.CourseRepository;
import lab3.repository.StudentRepository;
import lab3.repository.TeacherRepository;
import java.util.List;

public class Main extends Application {

    public static Stage primaryStage;
    public static Subject subject;

    // repos where we perform actions
    private static CourseRepository courseRepo;
    private static StudentRepository studentRepo;
    private static TeacherRepository teacherRepo;
    private static RegistrationSystem regSys;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Main.primaryStage = primaryStage;
        Parent root;
        root = FXMLLoader.load(getClass().getResource("screens/Start.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("L6");
        primaryStage.show();
    }

    public static void main(String[] args) {
        // INITIALIZING REPOSITORIES
        FileRepository teacherFileRepo = new TeacherFileRepo("teachers.json");
        FileRepository studentsFileRepo = new StudentFileRepo("students.json");

        List<Student> allStudents = studentsFileRepo.getObjects();
        List<Teacher> allTeachers = teacherFileRepo.getObjects();

        StudentRepository studentRepo = new StudentRepository(allStudents);
        TeacherRepository teacherRepo = new TeacherRepository(allTeachers);

        CourseFileRepo courseFileRepo = new CourseFileRepo("courses.json",studentRepo,teacherRepo);
        List<Course> allCourses = courseFileRepo.getObjects();

        CourseRepository courseRepo = new CourseRepository(allCourses);
        courseFileRepo.setRelations(courseRepo);

        // setting class attributes
        setCourseRepo(courseRepo);
        setStudentRepo(studentRepo);
        setTeacherRepo(teacherRepo);
        setRegSys(new RegistrationSystem(courseRepo));

        // INITIALIZING OBSERVER
        setSubject(new Subject());
        getSubject().setState(allCourses);
        new CoursesObserver(getSubject());


        launch(args);

        // saving all modifications back to the database after closing app

        // clearing files for old data and make room for new one
        courseFileRepo.clear();
        studentsFileRepo.clear();
        teacherFileRepo.clear();

        for (Course c:allCourses)
            courseFileRepo.add(c);

        for (Student s:allStudents)
            studentsFileRepo.add(s);

        for (Teacher t:allTeachers)
            teacherFileRepo.add(t);
    }

    // GETTERS AND SETTERS

    public static CourseRepository getCourseRepo() {
        return courseRepo;
    }

    public static void setCourseRepo(CourseRepository courseRepo) {
        Main.courseRepo = courseRepo;
    }

    public static StudentRepository getStudentRepo() {
        return studentRepo;
    }

    public static void setStudentRepo(StudentRepository studentRepo) {
        Main.studentRepo = studentRepo;
    }

    public static TeacherRepository getTeacherRepo() {
        return teacherRepo;
    }

    public static void setTeacherRepo(TeacherRepository teacherRepo) {
        Main.teacherRepo = teacherRepo;
    }

    public static RegistrationSystem getRegSys() {
        return regSys;
    }

    public static void setRegSys(RegistrationSystem regSys) {
        Main.regSys = regSys;
    }

    public static Subject getSubject() {
        return subject;
    }

    public static void setSubject(Subject subject) {
        Main.subject = subject;
    }
}