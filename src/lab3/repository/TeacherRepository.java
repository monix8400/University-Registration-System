package lab3.repository;
import lab3.model.Teacher;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TeacherRepository implements ICrudRepository<Teacher> {

    List<Teacher> allTeachers;

    /**
     * constructor
     * @param allTeachers initializer
     */
    public TeacherRepository(List<Teacher> allTeachers) {
        this.allTeachers = allTeachers;
    }

    /**
     * returns a teacher with the given firstName and lastName
     * @param firstName to search
     * @param lastName to search
     * @return corresponding teacher
     */
    public Teacher findTeacherByName(String firstName, String lastName) {
        return this.allTeachers.stream()
                .filter(s -> s.getFirstName().equals(firstName) && s.getLastName().equals(lastName))
                .findAny()
                .orElse(null);
    }

    /**
     * gives the teachers with the given id
     * @param id -the id of the entity to be returned id must not be null
     * @return null-if there's no teacher with given id, otherwise teacher
     */
    @Override
    public Teacher findOne(Long id) {
        // REFACTORED
        return this.allTeachers.stream()
                .filter(t -> t.getTeacherID() == id)
                .findAny()
                .orElse(null);
    }

    /**
     * gives all the list with teachers
     * @return allTeachers, the list of teachers
     */
    @Override
    public Iterable<Teacher> findAll() {
        return allTeachers;
    }

    /**
     * adds a new teacher to the list of teachers
     * @param entity entity must be not null
     * @return entity if it is not null, otherwise null
     */
    @Override
    public Teacher save(Teacher entity) {
        if(findOne(entity.getTeacherID())!=null)
            return entity;
        else
            allTeachers.add(entity);
        return null;
    }

    /**
     * removes the entity with the specified id
     * @param id id must be not null
     * @return null if the id can't be found, otherwise the deleted teacher
     */
    @Override
    public Teacher delete(Long id) {
        if (findOne(id) == null)
            return null;
        else {
            Teacher temp=findOne(id);
            allTeachers.remove(temp);
            return temp;
        }
    }

    /**
     * modifies the entity with the specified id
     * @param id entity to be updated
     * @param entity entity must not be null
     * @return null, if the teacher exists and teacher ID to be updated must be the same with the new teacher ID, otherwise the old entity
     */
    @Override
    public Teacher update(Long id, Teacher entity) {
        if (findOne(id) != null && id == entity.getTeacherID()) {
            allTeachers.add(entity);
            delete(id);
            return null;
        }
        else {
            return entity;
        }
    }

    /**
     *
     * @return the list of all teachers sorted lexicographically after teachers last name
     */
    @Override
    public List<Teacher> sort() {
        return allTeachers.stream()
                .sorted(Comparator.comparing(Teacher::getLastName))
                .collect(Collectors.toList());
    }

    /**
     * @return the filtered list of teachers List by the length of their first name
     */
    @Override
    public List<Teacher> filter(int length) {
        return allTeachers.stream()
                .filter(str -> str.getFirstName().length() > length)
                .collect(Collectors.toList());
    }

}
