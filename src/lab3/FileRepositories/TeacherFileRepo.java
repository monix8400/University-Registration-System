package lab3.FileRepositories;

import lab3.model.Course;
import lab3.model.Teacher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeacherFileRepo implements FileRepository<Teacher>{
    private final String filename;

    public TeacherFileRepo(String filename) {
        this.filename = filename;
    }

    /**
     * @return desired list of objects
     */
    @Override
    public List<Teacher> getObjects() {
        JSONArray list = read(filename);
        List<Teacher> teachers = new ArrayList<>();

        for (Object obj :list) {
            teachers.add(parseObject((JSONObject) obj));
        }

        return teachers;
    }

    /**
     * empties json file
     */
    @Override
    public void clear() {
        JSONArray empty = new JSONArray();
        try (FileWriter file = new FileWriter("src/lab3/database/" + filename)) {
            file.write(empty.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param obj is new entity to be written in json file
     */
    @SuppressWarnings("unchecked")
    @Override
    public void add(Teacher obj) {
        // New object
        JSONObject objectDetails = new JSONObject();
        objectDetails.put("id", obj.getTeacherID());
        objectDetails.put("firstName", obj.getFirstName());
        objectDetails.put("lastName", obj.getLastName());

        JSONObject teacherObject = new JSONObject();
        teacherObject.put("teacher", objectDetails);

        JSONArray teacherList = read(filename);
        teacherList.add(teacherObject);

        //Write JSON file
        try (FileWriter file = new FileWriter("src/lab3/database/" + filename)) {

            file.write(teacherList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param teacher
     * @return teacher object
     */
    public Teacher parseObject(JSONObject teacher)
    {
        //Get object within list
        JSONObject studentObject = (JSONObject) teacher.get("teacher");

        String firstName = (String) studentObject.get("firstName");
        String lastName = (String) studentObject.get("lastName");
        long id = (Long) studentObject.get("id");
        List<Course> courses = new ArrayList<>();

        return new Teacher(courses,id,firstName,lastName);
    }
}
