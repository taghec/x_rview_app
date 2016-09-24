package com.avilagroup.dev.x_rview_app;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.avilagroup.dev.x_rview_app.databinding.ActivityRviewListBinding;
import com.avilagroup.dev.x_rview_app.model.Person;
import com.avilagroup.dev.x_rview_app.util.PersonAdapter;
import com.avilagroup.dev.x_rview_app.util.PersonUtil;

import java.util.List;

public class RviewListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_rview_list);
        setTitle(getString(R.string.app_list_act_name) + " RecyclerView");
        final ActivityRviewListBinding rviewListBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_rview_list);

        /**
         * See comment on 'PersonBinding' activity reg
         * data/utility class instance.
         */
        final PersonUtil personUtil = new PersonUtil();
        List<Person> persons = personUtil.getPersons();

        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rviewListBinding.rvRviewList.setLayoutManager(layoutManager);

        final RecyclerView.Adapter personsAdapter = new PersonAdapter(persons);
        rviewListBinding.rvRviewList.setAdapter(personsAdapter);
    }
}
