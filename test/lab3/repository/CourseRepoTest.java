package lab3.repository;

import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;
import lab3.repository.CourseRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CourseRepoTest {

    List<Course> coursesByT1 = new ArrayList<>();
    List<Course> coursesByT2 = new ArrayList<>();
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

    List<Course> allcourses = new ArrayList<>();

    CourseRepository courseRepo = new CourseRepository(allcourses);

    @Test
    public void testFindOne(){
        courseRepo.save(c1);
        courseRepo.save(c2);
        courseRepo.save(c3);
        courseRepo.findOne(c2.getID());
        Assert.assertEquals(c2, courseRepo.findOne(c2.getID()));
    }

    @Test
    public void testFindAll(){
        courseRepo.save(c1);
        courseRepo.save(c2);
        courseRepo.save(c3);
        Assert.assertSame(allcourses,courseRepo.findAll());
        courseRepo.findAll();
    }

    @Test
    public void testSave() {
        courseRepo.save(c1);
        courseRepo.save(c2);
        Assert.assertEquals(2, allcourses.size());
        courseRepo.save(c2);
        courseRepo.save(c3);
        Assert.assertEquals(3, allcourses.size()); //only 3 courses case c2 x2
    }

    @Test
    public void testDelete(){
        courseRepo.save(c1);
        courseRepo.save(c2);
        courseRepo.save(c3);
        courseRepo.delete(c2.getID());
        Assert.assertEquals(2, allcourses.size());
        Assert.assertNull(courseRepo.delete(c2.getID()));
        Assert.assertEquals(3,courseRepo.delete(c4.getID()).getID());
    }

    @Test
    public void TestUpdate(){
        courseRepo.save(c1);
        courseRepo.save(c2);
        courseRepo.save(c3);
        Assert.assertNotEquals(null, courseRepo.update(c3.getID(),c5));
        Assert.assertEquals(c5, courseRepo.update(c3.getID(),c5));
        Assert.assertNull(courseRepo.update(c3.getID(), c4));
    }

    @Test
    public void testTeacherDeleteCourse() {
        courseRepo.save(c1);
        courseRepo.save(c2);
        courseRepo.save(c3);
        courseRepo.TeacherDeleteCourse(t2, c3); //teacher don't have courses
        Assert.assertNotEquals(2, allcourses.size());
        Assert.assertEquals(3, allcourses.size());

        coursesByT2.add(c3); //add course to teachers courseList
        courseRepo.TeacherDeleteCourse(t2, c3);
        Assert.assertEquals(2, allcourses.size());
    }

    @Test
    public void TestSort(){
        courseRepo.save(c1);
        courseRepo.save(c2);
        courseRepo.save(c3);
        courseRepo.save(c5);
        List<Course> sortedList =courseRepo.sort();  //c1,c3,c4,c2
        Assert.assertEquals(allcourses.get(0),sortedList.get(0));
        Assert.assertEquals(allcourses.get(1),sortedList.get(3));
        Assert.assertNotEquals(allcourses.get(1),sortedList.get(1));
        Assert.assertEquals(allcourses.get(2),sortedList.get(1));
        Assert.assertEquals(allcourses.get(3),sortedList.get(2));
    }

    @Test
    public void TestFilter(){
        courseRepo.save(c1);
        courseRepo.save(c2);
        courseRepo.save(c3);
        courseRepo.save(c5);
        List<Course> filteredList = courseRepo.filter(2);
        Assert.assertNotEquals(5,filteredList.size());
        Assert.assertEquals(3,filteredList.size());
    }
}
