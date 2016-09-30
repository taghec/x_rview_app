package com.avilagroup.dev.x_rview_app;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.avilagroup.dev.x_rview_app.databinding.ActivityRviewTwoAsyncListBinding;
import com.avilagroup.dev.x_rview_app.model.Person;
import com.avilagroup.dev.x_rview_app.model.PersonParsed_Obs;
import com.avilagroup.dev.x_rview_app.util.asyncGetPeopleTwo;
import com.avilagroup.dev.x_rview_app.util.cvPersonAdapter;

import java.util.ArrayList;
import java.util.List;

public class RviewTwoAsyncListActivity
        extends AppCompatActivity {
    private List<PersonParsed_Obs> mPersons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_name) + " - Rview Two");
//        setContentView(R.layout.activity_rview_two_async_list);

        /**
         * DataBind to RV view - View Manager
         *
         * The act class is auto-gen by DataBinding in android.
         */
        final ActivityRviewTwoAsyncListBinding rviewBinding = DataBindingUtil
                .setContentView(RviewTwoAsyncListActivity.this, R.layout.activity_rview_two_async_list);
        /**
         * DataBind to RV Holder view - View Holder
         *
         * will leave the async class specific for data call, no layout or UI
         * management. The method rvAsync- is auto-gen from the RV obj id in RView layout.
         */
        final RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(this);
        rviewBinding.rvAsyncLayoutTwo.setLayoutManager(rvLayoutManager);
        /**
         * DataBind to RV model - list adapter
         */
//        final RecyclerView.Adapter personsAdapter = new cvPersonAdapter(this, this.mPersons);
//        rviewBinding.rvAsyncLayoutTwo.setAdapter(personsAdapter);

        /**
         * Complete the data async-call.
         */
        new asyncGetPeopleTwo(this, rviewBinding, mPersons).execute();
    }

}
