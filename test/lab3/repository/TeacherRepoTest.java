package lab3.repository;

import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;
import lab3.repository.TeacherRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TeacherRepoTest {
    List<Course> coursesByT1 = new ArrayList<>();
    List<Course> coursesByT2 = new ArrayList<>();
    List<Course> coursesByT3 = new ArrayList<>();
    List<Course> coursesByT4 = new ArrayList<>();
    List<Course> coursesEnrolledS1 = new ArrayList<>();
    List<Course> coursesEnrolledS2 = new ArrayList<>();
    List<Student> studentEnrolledBD = new ArrayList<>();
    List<Student> studentEnrolledMAP = new ArrayList<>();

    Teacher t1 = new Teacher(coursesByT1, 1, "Diana", "Troanca");
    Course c1 = new Course(1, "BD", t1, 60, studentEnrolledBD, 6);
    Course c2 = new Course(2, "SDA", t1, 60, studentEnrolledBD, 4);

    Student s1 = new Student("elon", "musk", 1, 12, coursesEnrolledS1);
    Student s2 = new Student("jeff", "bezos", 2, 28, coursesEnrolledS2);

    Teacher t2 = new Teacher(coursesByT2, 2, "Catalin", "Rusu");
    Course c3 = new Course(3, "MAP", t2, 60, studentEnrolledMAP, 6);
    Course c4 = new Course(3, "FP", t2, 60, studentEnrolledMAP, 6);
    Course c5 = new Course(5, "OOP", t2, 60, studentEnrolledMAP, 6);

    Teacher t3 = new Teacher(coursesByT3, 3, "Florin", "Albisor");
    Teacher t4 = new Teacher(coursesByT4, 4, "Tudor", "Chifor");
    Teacher t5 = new Teacher(coursesByT4, 4, "Mada", "Dicu");

    List<Teacher> allTeachers = new ArrayList<>();

    TeacherRepository teacherRepo = new TeacherRepository(allTeachers);

    @Test
    public void testFindOne(){
        teacherRepo.save(t1);
        teacherRepo.save(t2);
        teacherRepo.save(t3);
        teacherRepo.findOne(t2.getTeacherID());
        Assert.assertEquals(t2, teacherRepo.findOne(t2.getTeacherID()));
    }

    @Test
    public void testFindAll(){
        teacherRepo.save(t1);
        teacherRepo.save(t2);
        teacherRepo.save(t3);
        Assert.assertSame(allTeachers,teacherRepo.findAll());
        teacherRepo.findAll();
    }

    @Test
    public void testSave() {
        teacherRepo.save(t1);
        teacherRepo.save(t2);
        Assert.assertEquals(2, allTeachers.size());
        teacherRepo.save(t2);
        teacherRepo.save(t3);
        Assert.assertEquals(3, allTeachers.size()); //only 3 courses case c2 x2
    }

    @Test
    public void testDelete(){
        teacherRepo.save(t1);
        teacherRepo.save(t2);
        teacherRepo.save(t3);
        teacherRepo.delete(t2.getTeacherID());
        Assert.assertEquals(2, allTeachers.size());
        Assert.assertNull(teacherRepo.delete(t2.getTeacherID()));
        Assert.assertNull(teacherRepo.delete(t4.getTeacherID()));
    }

    @Test
    public void TestUpdate(){
        teacherRepo.save(t1);
        teacherRepo.save(t2);
        teacherRepo.save(t3);
        teacherRepo.save(t4);
        Assert.assertNotEquals(null, teacherRepo.update(t3.getTeacherID(),t5));
        Assert.assertEquals(t5,teacherRepo.update(t3.getTeacherID(), t5));
        System.out.println(teacherRepo.update(t3.getTeacherID(),t5));
        Assert.assertNull( teacherRepo.update(t4.getTeacherID(),t5));
    }

    @Test
    public void TestSort(){
        teacherRepo.save(t1);
        teacherRepo.save(t2);
        teacherRepo.save(t3);
        teacherRepo.save(t4);
        List<Teacher> sortedList=teacherRepo.sort();  //t3,t4,t2,t1
        Assert.assertEquals(allTeachers.get(0),sortedList.get(3));
        Assert.assertEquals(allTeachers.get(1),sortedList.get(2));
        Assert.assertNotEquals(allTeachers.get(1),sortedList.get(1));
        Assert.assertEquals(allTeachers.get(2),sortedList.get(0));
        Assert.assertEquals(allTeachers.get(3),sortedList.get(1));

    }

    @Test
    public void TestFilter(){
        teacherRepo.save(t1);
        teacherRepo.save(t2);
        teacherRepo.save(t3);
        teacherRepo.save(t4);
        List<Teacher> filteredList = teacherRepo.filter(5);
        Assert.assertNotEquals(5,filteredList.size());
        Assert.assertEquals(2,filteredList.size());
    }

}
