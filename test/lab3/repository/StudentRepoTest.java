package lab3.repository;

import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;
import lab3.repository.StudentRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StudentRepoTest {

    List<Course> coursesByT1 = new ArrayList<>();
    List<Course> coursesByT2 = new ArrayList<>();
    List<Course> coursesEnrolledS1 = new ArrayList<>();
    List<Course> coursesEnrolledS2 = new ArrayList<>();
    List<Student> studentEnrolledBD = new ArrayList<>();
    List<Student> studentEnrolledMAP = new ArrayList<>();

    Teacher t1 = new Teacher(coursesByT1, 1, "Diana", "Troanca");
    Course c1 = new Course(1, "BD", t1, 60, studentEnrolledBD, 6);
    Course c2 = new Course(2, "SDA", t1, 60, studentEnrolledBD, 4);

    Student s1 = new Student("Elon", "Musk", 1, 12, coursesEnrolledS1);
    Student s2 = new Student("Jeff", "Bezos", 2, 28, coursesEnrolledS2);
    Student s3 = new Student("Bill", "Gates", 3, 30, coursesEnrolledS2);
    Student s4 = new Student("Mark", "Zuckerberg", 4, 15, coursesEnrolledS1);
    Student s5 = new Student("Steve", "Jobs", 3, 10, coursesEnrolledS1);

    Teacher t2 = new Teacher(coursesByT2, 2, "Catalin", "Rusu");
    Course c3 = new Course(3, "MAP", t2, 60, studentEnrolledMAP, 6);
    Course c4 = new Course(3, "FP", t2, 60, studentEnrolledMAP, 6);
    Course c5 = new Course(5, "OOP", t2, 60, studentEnrolledMAP, 6);

    List<Student> allStud = new ArrayList<>();

    StudentRepository studRepo = new StudentRepository (allStud);;

    @Test
    public void testFindOne(){
        studRepo.save(s1);
        studRepo.save(s2);
        studRepo.findOne(s2.getStudentID());
        Assert.assertEquals(s2, studRepo.findOne(s2.getStudentID()));
    }
    @Test
    public void testFindAll(){
        studRepo.save(s1);
        studRepo.save(s2);
        Assert.assertSame(allStud,studRepo.findAll());
        studRepo.findAll();
    }

    @Test
    public void testSave() {
        studRepo.save(s1);
        studRepo.save(s2);
        Assert.assertEquals(2, allStud.size());
        studRepo.save(s2);
        studRepo.save(s3);
        Assert.assertEquals(3, allStud.size()); //only 3 courses case c2 x2
    }

    @Test
    public void testDelete(){
        studRepo.save(s1);
        studRepo.save(s2);
        studRepo.save(s3);
        studRepo.delete(s2.getStudentID());
        Assert.assertEquals(2, allStud.size());
        Assert.assertNull(studRepo.delete(s2.getStudentID()));
        Assert.assertNull(studRepo.delete(s4.getStudentID()));
    }

    @Test
    public void TestUpdate(){
        studRepo.save(s1);
        studRepo.save(s2);
        studRepo.save(s3);
        Assert.assertNotEquals(null, studRepo.update(s3.getStudentID(),s4));
        Assert.assertEquals(s5, studRepo.update(s4.getStudentID(),s5));
        Assert.assertNull(studRepo.update(s3.getStudentID(), s5));
    }

    @Test
    public void TestSort(){
       studRepo.save(s1);
       studRepo.save(s2);
       studRepo.save(s3);
       studRepo.save(s4);
       List<Student> sortedList =studRepo.sort();  //s2,s3,s1,s4
       Assert.assertEquals(allStud.get(0),sortedList.get(2));
       Assert.assertEquals(allStud.get(1),sortedList.get(0));
       Assert.assertNotEquals(allStud.get(1),sortedList.get(1));
       Assert.assertEquals(allStud.get(2),sortedList.get(1));
       Assert.assertEquals(allStud.get(3),sortedList.get(3));
    }

    @Test
    public void TestFilter(){
       studRepo.save(s1);
       studRepo.save(s2);
       studRepo.save(s3);
       studRepo.save(s4);
        System.out.println(studRepo.findAll());
       List<Student> filteredList =studRepo.filter(4);
        System.out.println(filteredList);
       Assert.assertNotEquals(5,filteredList.size());
       Assert.assertEquals(0,filteredList.size());
    }
}
