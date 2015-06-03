package controllers;

import models.Person;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 6/3/2015.
 */
public class PersonController extends Controller{
    public Result listPerson() {
        Iterable<Person> persons = Person.findAll();
        String buffer = "";
        for (Person person : persons) {
            buffer += person.toString() + "\n";
        }
        return ok(buffer);
    }

    public Result getPerson(String name) {
        Iterable<Person> persons = Person.findByName(name);
        String buffer = "";
        for (Person person : persons) {
            buffer += person.toString() + "\n";
        }
        return ok(buffer);
    }

    public Result insertPerson() {
        Map<String, String[]> data = Controller.request().body().asFormUrlEncoded();
        String name = data.get("name")[0];
        String status = data.get("status")[0];
        int income = Integer.parseInt(data.get("income")[0]);
        Person person = new Person(name, status, income);
        return person.insert()? ok("Insert Succeed") : ok("Insert Failed");
    }

    public Result deletePerson() {
        String name = Controller.request().body().asFormUrlEncoded().get("name")[0];
        int deleted = Person.removeByName(name);
        return ok(deleted+" documents deleted");
    }

    public Result updatePerson() {
        Map<String, String[]> data = Controller.request().body().asFormUrlEncoded();
        String name = data.get("name")[0];

        Map<String, String> params = new HashMap<String, String>(data.size());
        for (String param : data.keySet()) {
            if (param.compareTo("name") != 0) {
                params.put(param, data.get(param)[0]);
            }
        }

        int updated = Person.updateByName(name, params);
        return ok(updated+" persons updated");
    }
}
