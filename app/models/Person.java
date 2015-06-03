package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.MongoCollection;
import utils.DB;

import java.util.Map;

/**
 * Created by pxajie on 5/30/2015.
 */
public class Person {
    @JsonProperty("_id")
    String id;
    String name;
    String status;

    int income;

    public Person () {}

    public Person (String name, String hobby, int income) {
        this.name = name;
        this.status = hobby;
        this.income = income;
    }

    private static MongoCollection coll () {
        return DB.jongo.getCollection("person");
    }

    public static Iterable<Person> findByName (String name){
        return coll().find("{name: #}", name).as(Person.class);
    }


    public static Iterable<Person> findAll() {
        return coll().find().as(Person.class);
    }

    /**
     * Use this function to remove person document by name
     * @param name  name of the person
     * @return      number of documents affected by query
     */
    public static int removeByName(String name) {
        return coll().remove("{name: #}", name).getN();
    }

    /**
     * Use this function to update person document by name
     * @param name      name of the person
     * @param params    document attribute to update
     * @return          number of documents affected by query
     */
    public static int updateByName(String name, Map<String, String> params) {
        StringBuilder updateQuery = new StringBuilder("{$set:{");
        for (String key : params.keySet()) {
            String param = params.get(key);
            if (isNumeric(param)) {
                updateQuery.append(key+": "+param+",");
            } else {
                updateQuery.append(key+": '"+param+"',");
            }
        }
        updateQuery.append("}}");
        return coll().update("{name: #}", name).with(updateQuery.toString()).getN();
    }

    public boolean insert() {
        try {
            coll().insert(this);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "id: "+id+", name: "+name+", hobby: "+status+", income: "+income;
    }
}
