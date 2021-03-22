package lab3;

import lab3.controller.CourseController;
import lab3.exceptions.CourseNotFoundException;
import lab3.exceptions.StudentNotFoundException;
import lab3.exceptions.TeacherNotFoundException;
import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;
import lab3.repository.CourseRepository;
import lab3.repository.StudentRepository;
import lab3.repository.TeacherRepository;
import lab3.view.CourseView;
import lab3.view.StudentView;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private static RegistrationSystem regSys;
    CourseRepository courseRepo;
    StudentRepository studentRepo;
    TeacherRepository teacherRepo;

    /**
     * constructor of the menu, takes those 3 arguments which are the actual repositories of the application
     * @param courseRepo
     * @param studentRepo
     * @param teacherRepo
     */
    public Menu(CourseRepository courseRepo, StudentRepository studentRepo, TeacherRepository teacherRepo) {
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        regSys = new RegistrationSystem(courseRepo);
    }

    /**
     * CONSOLE of the app, user can interract with the app using this function ran in the StartApp
     * @throws CourseNotFoundException
     * @throws StudentNotFoundException
     * @throws TeacherNotFoundException
     */
    public void printMenu() throws CourseNotFoundException, StudentNotFoundException, TeacherNotFoundException {
        //Get user's choice
        int choice;
        do {
            Scanner in = new Scanner(System.in);
            // Display the menu
            System.out.println("1\t Enroll to course");
            System.out.println("2\t Print available courses");
            System.out.println("3\t Print all courses");
            System.out.println("4\t Print students enrolled to specific course");
            System.out.println("5\t Delete course as a teacher");
            System.out.println("6\t Update course as a teacher");
            System.out.println("7\t Print students");
            System.out.println("0\t Exit");

            System.out.println("Please enter your choice:");
            choice=in.nextInt();

            //Display the title of the chosen module
            switch (choice) {
                case 1: System.out.println("Enroll to course");
                    System.out.println("-- Select course:");
                    selectCourse(courseRepo.findAll());
                    Scanner courseID = new Scanner(System.in);

                    Course courseToEnroll = courseRepo.findOne(courseID.nextLong());
                    if (courseToEnroll == null) {
                        throw new CourseNotFoundException();
                    }

                    System.out.println("-- Select student:");
                    selectStudent(studentRepo.findAll());
                    Scanner studentID = new Scanner(System.in);
                    Student studentToEnroll = studentRepo.findOne(studentID.nextLong());
                    if (studentToEnroll == null) {
                        throw new StudentNotFoundException();
                    }

                    boolean enrolledSuccessfully = regSys.register(courseToEnroll, studentToEnroll);

                    if (enrolledSuccessfully)
                        System.out.println("Enrollment succesfully");
                    else
                        System.out.println("Student already enrolled or no more free places, please choose another course.");

                    break;
                case 2: System.out.println("Print available courses");
                    printAvailableCourses();
                    break;
                case 3: System.out.println("Print all courses");
                    printCourses();
                    break;
                case 4: System.out.println("Print students enrolled to specific course");
                    System.out.println("-- Select course:");
                    selectCourse(courseRepo.findAll());
                    Scanner cID = new Scanner(System.in);
                    Course specificCourse = courseRepo.findOne(cID.nextLong());
                    if (specificCourse == null) {
                        throw new CourseNotFoundException();
                    }

                    regSys.printSpecificCourseDetails(specificCourse);
                    break;
                case 5: System.out.println("Delete course as a teacher");
                    System.out.println("-- Select course:");
                    selectCourse(courseRepo.findAll());
                    Scanner teacherCourse = new Scanner(System.in);
                    Course courseOfTeacher = courseRepo.findOne(teacherCourse.nextLong());
                    if (courseOfTeacher == null) {
                        throw new CourseNotFoundException();
                    }

                    System.out.println("-- Select teacher:");
                    selectTeacher(teacherRepo.findAll());
                    Scanner teacherID = new Scanner(System.in);
                    Teacher teacherToDeleteCourse = teacherRepo.findOne(teacherID.nextLong());
                    if (teacherToDeleteCourse == null) {
                        throw new TeacherNotFoundException();
                    }

                    courseRepo.TeacherDeleteCourse(teacherToDeleteCourse, courseOfTeacher);

                    break;
                case 6: System.out.println("Update course as a teacher");
                    System.out.println("-- Select course:");
                    selectCourse(courseRepo.findAll());
                    Scanner courseUpdateID = new Scanner(System.in);
                    long courseToBeModified = courseUpdateID.nextLong();
                    Course courseUpdate = courseRepo.findOne(courseToBeModified);
                    if (courseUpdate == null) {
                        throw new CourseNotFoundException();
                    }

                    System.out.println("-- Select teacher:");
                    selectTeacher(teacherRepo.findAll());
                    Scanner teacherUpdateID = new Scanner(System.in);
                    Teacher teacherUpdate = teacherRepo.findOne(teacherUpdateID.nextLong());
                    if (teacherUpdate == null) {
                        throw new TeacherNotFoundException();
                    }

                    Scanner reader = new Scanner(System.in);
                    System.out.println("New name:");
                    String name = reader.nextLine();
                    System.out.println("New maxEnrollment:");
                    int maxEnrollment = reader.nextInt();
                    System.out.println("New maxCredits:");
                    int credits = reader.nextInt();

                    Course c_new = new Course(courseToBeModified,name,teacherUpdate,maxEnrollment,courseUpdate.getStudentsEnrolled(),credits);

                    courseRepo.update(courseToBeModified, c_new);

                    // update course in students course list
                    for(Student s:studentRepo.findAll()) {
                        List<Course> enrolledCourses = s.getEnrolledCourses();
                        for(Course c:enrolledCourses) {
                            // updating student course and credits if he owns the course
                            if (c.getID() == courseToBeModified) {
                                enrolledCourses.remove(c);
                                enrolledCourses.add(c_new);
                                s.setEnrolledCourses(enrolledCourses);

                                // credits
                                s.setTotalCredits(s.getTotalCredits() - c.getCredits() + c_new.getCredits());
                                break;
                            }
                        }
                    }

                    break;
                case 7:System.out.println("Print students.");
                    StudentView studView = new StudentView();
                    for (Student s:studentRepo.findAll()) {
                        studView.printStudentDetails(s);
                    }
                    break;
                case 0: System.out.println("Exit.");
                    break;
                default: System.out.println("Invalid choice");
            }//end of switch
        }while(choice != 0);
    }

    /**
     * prints a list with all courses out of the given list
     * @param list
     */
    public static void selectCourse(Iterable<Course> list) {
        for (Course c:list) {
            System.out.println(c.getID() + " " + c.getName());
        }
    }

    /**
     * prints a list with all students out of the given list
     * @param list
     */
    public static void selectStudent(Iterable<Student> list) {
        for (Student s:list) {
            System.out.println(s.getStudentID() + " " + s.getFirstName() + " " + s.getLastName());
        }
    }

    /**
     * prints a list with all teachers out of the given list
     * @param list
     */
    public static void selectTeacher(Iterable<Teacher> list) {
        for (Teacher t:list) {
            System.out.println(t.getTeacherID() + " " + t.getFirstName() + " " + t.getLastName());
        }
    }

    /**
     * prints a list with all courses from the repo
     */
    public void printCourses() {
        List<Course> courses = regSys.getAllCourses();
        printMethod(courses);
    }

    /**
     * prints all courses with free places from the repo
     */
    public void printAvailableCourses() {
        List<Course> courses = regSys.retrieveCoursesWithFreePlaces();
        printMethod(courses);
    }

    /**
     * function to print courses given in the courses list as param
     * @param courses
     */
    public static void printMethod(List<Course> courses) {
        for (Course c: courses) {
            CourseView view = new CourseView();
            CourseController controller = new CourseController(c, view);
            controller.showView();
        }
    }
}
