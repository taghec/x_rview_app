package com.avilagroup.dev.x_rview_app.util;

import com.avilagroup.dev.x_rview_app.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by taghec on 21/09/2016.
 */

public class PersonUtil extends Person {
//    private List<Person> persons;
//    static List<Person> persons = new ArrayList<Person>();

    public PersonUtil(String firstname, String lastname, int age, String gender) {
        super(firstname, lastname, age, gender);
    }

    public static List<Person> initPersons(){
        List<Person> persons = new ArrayList<Person>();

        for (int x=1; x<=100; x++) {
            persons.add(new Person("Person " + x, "Last" + x, 20 + x, "female"));
        }

//        this.persons = persons;
        return persons;
    }

    public static List<Person> getPersons() {
//        persons.add(new Person("Lenni","Avi",3,"male"));
//        persons.add(new Person("John", "McEnroe", 90,"male"));

        return initPersons();
    }

    public static Person getPersonRandom() {
        List<Person> persons = getPersons();
        return persons.get(new Random().nextInt(persons.size()));
    }
}
