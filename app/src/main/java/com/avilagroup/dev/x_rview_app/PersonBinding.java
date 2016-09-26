package com.avilagroup.dev.x_rview_app;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.avilagroup.dev.x_rview_app.databinding.ActivityPersonBindingBinding;
import com.avilagroup.dev.x_rview_app.model.Person;
import com.avilagroup.dev.x_rview_app.util.PersonUtil;

public class PersonBinding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_person_binding);
        final ActivityPersonBindingBinding personBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_person_binding);

//        Person aPerson = new Person("Lenni","Avi",3,"male");
//        Person aPerson = PersonUtil.getPersons().get(1);

        /**
         *  Modified the PersonUtil class. Now need to create
         * instance before using. This makes better use of
         * the single pass call for data, instead of
         * recreating in the class for every method.
         *
         * Mod: if there's a spec person/item req'd (in Intent), get it,
         * otherwise just get a random.
        */
        final PersonUtil personUtil = new PersonUtil();
        Person aPerson;
        int listLoc = this.getIntent().getIntExtra("listLoc",-1);

        if (listLoc >= 0){
            aPerson = personUtil.getPerson(listLoc);
        } else
            aPerson = personUtil.getPersonRandom();

        personBinding.setPerson(aPerson);
    }
}
