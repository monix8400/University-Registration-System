package lab3.repository;

import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CourseRepository implements ICrudRepository<Course>{

    private final List<Course> courses;

    /**
     * constructor
     * @param courses initializer
     */
    public CourseRepository(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * returns a Course with the given name
     * @param name to search
     * @return corresponding teacher
     */
    public Course findCourseByName(String name) {
        return this.courses.stream()
                .filter(s -> s.getName().equals(name))
                .findAny()
                .orElse(null);
    }

    /**
     * gives the course with the given id
     * @param id -the id of the entity to be returned id must not be null
     * @return null-if there's no course with given id, otherwise course
     */
    @Override
    public Course findOne(Long id) {
        // REFACTORED
        return this.courses.stream()
                .filter(c -> c.getID() == id)
                .findAny()
                .orElse(null);
    }

    /**
     * gives all the list with courses
     * @return courses, the list of courses
     */
    @Override
    public Iterable<Course> findAll() {
        return courses;
    }

    /**
     * adds a new course to the list of courses
     * @param entity entity must be not null
     * @return entity if it is not null, otherwise null
     */
    @Override
    public Course save(Course entity) {
        if (findOne(entity.getID()) != null)
            return entity;
        else {
            courses.add(entity);
        }
        return null;
    }

    /**
     * removes the entity with the specified id
     * @param id id must be not null
     * @return null if the id can't be found, otherwise the deleted course
     */
    @Override
    public Course delete(Long id) {
        if (findOne(id) == null)
            return null;
        else {
            Course temp=findOne(id);
            courses.remove(temp);
            return temp;
        }
    }

    /**
     * modifies the entity with the specified id
     * @param id entity to be updated
     * @param entity entity must not be null
     * @return null if the course exists and course ID to be updated must be the same with the new course ID, otherwise the old entity
     */
    @Override
    public Course update(Long id, Course entity) {
        // course to be updated must exist and course ID to be updated must be the same with the new course ID
        if (findOne(id) != null && id == entity.getID()) {
            courses.add(entity);
            delete(id);

            return null;
        }
        else {
            return entity;
        }
    }

    /**
     * the specified teacher removes a course. The course must be removed from students lists, course lists and teachers list
     * @param t teacher
     * @param c course
     */
    public void TeacherDeleteCourse(Teacher t, Course c){
        boolean exists = false;
        for(Course course:t.getCourses()){
            if(c.getID()== course.getID()){
                exists = true;

                for(Student s: c.getStudentsEnrolled()){
                    List<Course> studentCourses = s.getEnrolledCourses();
                    for(Course studentCourse: studentCourses){
                        if(studentCourse.getID()== course.getID()) {
                            studentCourses.remove(c);
                            s.setEnrolledCourses(studentCourses);
                            s.setTotalCredits(s.getTotalCredits() - c.getCredits());
                            break;
                        }
                    }
                }
                delete(course.getID());
            }
        }
        if(!exists)
            System.out.println("teacher doesn't have " + c.getName() + " course");
    }

    /**
     *
     * @return the list of all students sorted lexicographically after students last name
     */
    @Override
    public List<Course> sort(){
        return courses.stream()
                .sorted(Comparator.comparing(Course::getName))
                .collect(Collectors.toList());
    }

    /**
     * @return the filtered list of courses List by the length of their name
     */
    @Override
    public List<Course> filter(int length) {
        return courses.stream()
                .filter(str -> str.getName().length() > length)
                .collect(Collectors.toList());
    }
}
