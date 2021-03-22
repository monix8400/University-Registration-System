package lab3;

import lab3.FileRepositories.CourseFileRepo;
import lab3.FileRepositories.FileRepository;
import lab3.FileRepositories.StudentFileRepo;
import lab3.FileRepositories.TeacherFileRepo;
import lab3.exceptions.CourseNotFoundException;
import lab3.exceptions.StudentNotFoundException;
import lab3.exceptions.TeacherNotFoundException;
import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;
import lab3.repository.*;
import java.util.List;

public class StartApp {
    public static void main(String[] args) throws CourseNotFoundException, StudentNotFoundException, TeacherNotFoundException {
        FileRepository teacherFileRepo = new TeacherFileRepo("teachers.json");
        FileRepository studentsFileRepo = new StudentFileRepo("students.json");

        // initializing repositories
        List<Student> allStudents = studentsFileRepo.getObjects();
        List<Teacher> allTeachers = teacherFileRepo.getObjects();

        StudentRepository studentRepo = new StudentRepository(allStudents);
        TeacherRepository teacherRepo = new TeacherRepository(allTeachers);

        CourseFileRepo courseFileRepo = new CourseFileRepo("courses.json",studentRepo,teacherRepo);
        List<Course> allCourses = courseFileRepo.getObjects();

        CourseRepository courseRepo = new CourseRepository(allCourses);
        courseFileRepo.setRelations(courseRepo);

        // menu handles a list of students, courses and teachers that are being read before launch
        Menu menu = new Menu(courseRepo,studentRepo,teacherRepo);
        menu.printMenu();

        // saving all modifications back to the database

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
}
