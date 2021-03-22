package lab3.controller;

import lab3.RegistrationSystem;
import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;
import lab3.repository.CourseRepository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RegistrationSystemTest {
    @Test
    public void testRegister() {
        List<Course> coursesByT1 = new ArrayList<>();
        List<Course> coursesEnrolledS1 = new ArrayList<>();
        List<Course> coursesEnrolledS2 = new ArrayList<>();
        List<Student> studentEnrolledBD = new ArrayList<>();

        Teacher t1 = new Teacher(coursesByT1,1, "Diana","Troanca");
        Course c1 = new Course(1,"BD",t1,60,studentEnrolledBD,6);

        Student s1 = new Student("elon","musk",1,12,coursesEnrolledS1);
        Student s2 = new Student("jeff","bezos",2,28,coursesEnrolledS2);

        List<Course> allCourses = new ArrayList<>();
        CourseRepository courseRepo = new CourseRepository(allCourses);
        RegistrationSystem regSys = new RegistrationSystem(courseRepo);

        // there are no students registered to c1
        assert c1.getStudentsEnrolled().size() == 0;

        // before registration
        assert regSys.register(c1, s1);

        // after already registered
        assert !regSys.register(c1, s1);

        // student s2 cannot register due to many credits
        assert !regSys.register(c1,s2);

        // there is 1 student registered to c1 which is "Elon Musk"
        assert c1.getStudentsEnrolled().size() == 1;
        assert c1.getStudentsEnrolled().get(0).getFirstName().equals("elon");
    }

    @Test
    public void testRetrieveCoursesWithFreeSpaces() {
        List<Course> coursesByT1 = new ArrayList<>();
        List<Student> studentEnrolled = new ArrayList<>();

        Teacher t1 = new Teacher(coursesByT1,1, "Diana","Troanca");
        Course c1 = new Course(1,"BD",t1,60,studentEnrolled,6);
        Course c2 = new Course(2,"Alg1",t1,1,studentEnrolled,6);
        Course c3 = new Course(3,"Geome",t1,0,studentEnrolled,6);

        List<Course> coursesEnrolled = new ArrayList<>();
        Student s1 = new Student("elon","musk",1,12,coursesEnrolled);
        Student s2 = new Student("jeff","bezos",2,28,coursesEnrolled);

        List<Course> allCourses = new ArrayList<>();
        CourseRepository courseRepo = new CourseRepository(allCourses);
        courseRepo.save(c1);
        courseRepo.save(c2);
        courseRepo.save(c3);

        RegistrationSystem regSys = new RegistrationSystem(courseRepo);

        // enrolling 2 example students to each of the 3 courses
        studentEnrolled.add(s1);
        studentEnrolled.add(s2);

        // only one course of the 3 will be returned because other 2 are full
        assert regSys.retrieveCoursesWithFreePlaces().size() == 1;

        // name of the returned course will be the first one with maxCapacity = 60
        assert regSys.retrieveCoursesWithFreePlaces().get(0).getName().equals("BD");
    }

    @Test
    public void testRetrieveStudentsEnrolledForACourse() {
        List<Course> coursesByT1 = new ArrayList<>();
        List<Student> studentEnrolled = new ArrayList<>();

        Teacher t1 = new Teacher(coursesByT1,1, "Diana","Troanca");
        Course c1 = new Course(1,"BD",t1,60,studentEnrolled,6);
        Course c2 = new Course(2,"Alg1",t1,1,studentEnrolled,6);
        Course c3 = new Course(3,"Geome",t1,0,studentEnrolled,6);

        List<Course> coursesEnrolled = new ArrayList<>();
        Student s1 = new Student("elon","musk",1,12,coursesEnrolled);
        Student s2 = new Student("jeff","bezos",2,28,coursesEnrolled);

        List<Course> allCourses = new ArrayList<>();
        CourseRepository courseRepo = new CourseRepository(allCourses);
        courseRepo.save(c1);
        courseRepo.save(c2);
        courseRepo.save(c3);

        RegistrationSystem regSys = new RegistrationSystem(courseRepo);

        // enrolling 2 example students to each of the 3 courses
        studentEnrolled.add(s1);
        studentEnrolled.add(s2);

        // there are 2 students enrolled to course c1
        assert regSys.retrieveStudentsEnrolledForACourse(c1).size() == 2;

        // c4 exists but it is not added to the repository so it wont return any students
        Course c4 = new Course(15,"Geome2",t1,0,studentEnrolled,6);
        assert regSys.retrieveStudentsEnrolledForACourse(c4) == null;
    }

    @Test
    public void testGetAllCourses() {
        List<Course> coursesByT1 = new ArrayList<>();
        List<Student> studentEnrolled = new ArrayList<>();

        Teacher t1 = new Teacher(coursesByT1,1, "Diana","Troanca");
        Course c1 = new Course(1,"BD",t1,60,studentEnrolled,6);
        Course c2 = new Course(2,"Alg1",t1,1,studentEnrolled,6);
        Course c3 = new Course(3,"Geome",t1,0,studentEnrolled,6);

        List<Course> coursesEnrolled = new ArrayList<>();
        Student s1 = new Student("elon","musk",1,12,coursesEnrolled);
        Student s2 = new Student("jeff","bezos",2,28,coursesEnrolled);

        List<Course> allCourses = new ArrayList<>();
        CourseRepository courseRepo = new CourseRepository(allCourses);
        courseRepo.save(c1);
        courseRepo.save(c2);
        courseRepo.save(c3);

        RegistrationSystem regSys = new RegistrationSystem(courseRepo);

        // 3 total courses
        assert regSys.getAllCourses().size() == 3;

        // first one is BD, second is Alg1, third is Geome
        assert regSys.getAllCourses().get(0).getName().equals("BD");
        assert regSys.getAllCourses().get(1).getName().equals("Alg1");
        assert regSys.getAllCourses().get(2).getName().equals("Geome");
    }
}
