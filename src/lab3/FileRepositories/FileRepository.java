package lab3.FileRepositories;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public interface FileRepository<E> {

    /**
     * function to write one object at a time in the desired .json file
     * @param obj is new entity to be written in json file
     */
    void add(E obj);

    /**
     * function to parse data from a JSONObject and form a new entity
     * @param obj jsonobject from which to parse stuff and then form the desired E entity
     * @returns E entity object after we parsed the data from json and we have a fresh object(Course, Teacher, Student)
     */
    E parseObject(JSONObject obj);

    /**
     * function that forms a list of desired E type elements to initialize the repositories with
     * used to initialize allCourses,allTeachers and allStudents
     * @returns the desired E type list
     */
    List<E> getObjects();

    /**
     * clears the json file and leaves new space to paste new allCourses, allStudents and allTeacbers
     */
    void clear();

    /**
     * function to get all data within a desired json fi;e
     * @param filename
     * @returns a JSONArray with the desired data found in the json file
     */
    default JSONArray read(String filename) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("src/lab3/database/" + filename))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            return (JSONArray) obj;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

}
