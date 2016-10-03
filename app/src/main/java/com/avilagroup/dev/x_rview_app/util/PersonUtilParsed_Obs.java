package com.avilagroup.dev.x_rview_app.util;

import android.util.Log;

import com.avilagroup.dev.x_rview_app.model.PersonParsed_Obs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by taghec on 03/10/2016.
 */

public class PersonUtilParsed_Obs {
    private static final int DEMO_LIST_SIZE = 100;
    private List<PersonParsed_Obs> persons;

    // CONSTRUCTOR
    public PersonUtilParsed_Obs() {
        this.persons = initPersons();
    }

    // DEMO GENERATOR
    private List<PersonParsed_Obs> initPersons() {
        List<PersonParsed_Obs> persons = new ArrayList<>();

        for (int i=1; i<=DEMO_LIST_SIZE; i++){
            persons.add(new PersonParsed_Obs("Last"+i, "First"+i));
            Log.d("PARSED: ","Generating person = ("+i+")");
        }

        return persons;
    }

    public List<PersonParsed_Obs> getPersons() {
        return this.persons;
    }
    public PersonParsed_Obs getRandomPerson() {
        return this.persons.get(new Random().nextInt(persons.size()));
    }
    public PersonParsed_Obs getPerson(int i) {
        return persons.get(i);
    }
}
