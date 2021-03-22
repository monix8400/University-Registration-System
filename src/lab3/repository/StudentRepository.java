package lab3.repository;
import lab3.model.Student;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StudentRepository implements ICrudRepository<Student>{

    List<Student> allStudents;

    /**
     * constructor
     * @param allStudents initializer
     */
    public StudentRepository(List<Student> allStudents){
        this.allStudents=allStudents;
    }

    /**
     * returns a student with the given firstName and lastName
     * @param firstName to search
     * @param lastName to search
     * @return corresponding student
     */
    public Student findStudentByName(String firstName, String lastName) {
        return this.allStudents.stream()
                .filter(s -> s.getFirstName().equals(firstName) && s.getLastName().equals(lastName))
                .findAny()
                .orElse(null);
    }

    /**
     * gives the student with the given id
     * @param id -the id of the entity to be returned id must not be null
     * @return null-if there's no student with given id, otherwise student
     */
    @Override
    public Student findOne(Long id) {
        // REFACTORED
        return this.allStudents.stream()
                .filter(s -> s.getStudentID() == id)
                .findAny()
                .orElse(null);
    }

    /**
     * gives all the list with students
     * @return allStudents, the list of students
     */
    @Override
    public Iterable<Student> findAll() {
        return allStudents;
    }

    /**
     * adds a new student to the list of students
     * @param entity entity must be not null
     * @return entity if it is not null, otherwise null
     */
    @Override
    public Student save(Student entity) {
        if(findOne(entity.getStudentID())!=null)
            return entity;
        else
            allStudents.add(entity);
        return null;
    }

    /**
     * removes the entity with the specified id
     * @param id id must be not null
     * @return null if the id can't be found, otherwise the deleted student
     */
    @Override
    public Student delete(Long id) {
        if (findOne(id) == null)
            return null;
        else {
            Student temp=findOne(id);
            allStudents.remove(temp);
            return temp;
        }
    }

    /**
     * modifies the entity with the specified id
     * @param id entity to be updated
     * @param entity entity must not be null
     * @return null, if the student exists and student ID to be updated must be the same with the new student ID, otherwise the old entity
     */
    @Override
    public Student update(Long id, Student entity) {
        if (findOne(id) != null && id == entity.getStudentID()) {
            allStudents.add(entity);
            delete(id);
            return null;
        }
        else {
            return entity;
        }
    }

    /**
     *
     * @return the list of all students sorted lexicographically after students last name
     */
    @Override
    public List<Student> sort() {
        return allStudents.stream()
                .sorted(Comparator.comparing(Student::getLastName))
                .collect(Collectors.toList());
    }

    /**
     * @return the filtered list of students List by the length of their first name
     */
    @Override
    public List<Student> filter(int length) {
        return allStudents.stream()
                .filter(str -> str.getFirstName().length() > length)
                .collect(Collectors.toList());
    }

}
