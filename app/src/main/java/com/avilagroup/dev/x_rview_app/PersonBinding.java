package com.avilagroup.dev.x_rview_app;

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
        Person aPerson = PersonUtil.getPersonRandom();
        personBinding.setPerson(aPerson);
    }
}
