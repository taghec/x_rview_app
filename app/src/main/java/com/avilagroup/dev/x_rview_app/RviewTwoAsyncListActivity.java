package com.avilagroup.dev.x_rview_app;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

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
                .setContentView(this, R.layout.activity_rview_two_async_list);
/* ----------------------------------
        */
/**
         * DataBind to RV Holder view - View Holder
         *
         * will leave the async class specific for data call, no layout or UI
         * management. The method rvAsync- is auto-gen from the RV obj id in RView layout.
         *//*

        final RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(this);
        rviewBinding.rvAsyncLayoutTwo.setLayoutManager(rvLayoutManager);
        */
/**
         * DataBind to RV model - list adapter
         *//*

//        final RecyclerView.Adapter personsAdapter = new cvPersonAdapter(this, this.mPersons);
//        rviewBinding.rvAsyncLayoutTwo.setAdapter(personsAdapter);

        */
/**
         * Complete the data async-call.
         *//*

        new asyncGetPeopleTwo(this, rviewBinding, mPersons).execute();
-------------------------------------- */

        /**
         * Async Call and RV Holder binding
         *
         * Instead of a separate the, I'll try to do this w an 'Observable' class
         * which is upposed to 'observe' changes to the bound data and update the
         * view automatically.  The challenge will then become persisting the data, but
         * I'll investigate that later.
         */
        new asyncGetPeopleTwo(this, rviewBinding).execute();

        /**
         * FAB binding on main act
         *
         * Attach action to FAB button
         */
        rviewBinding.fabPersonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RviewTwoAsyncListActivity.this,
                        R.string.stg_adding_entry,Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * Gesture Response
         *
         * Attach sliding gesture action
         */
        ItemTouchHelper.SimpleCallback slideCallback = new ItemTouchHelper
                .SimpleCallback(0,ItemTouchHelper.RIGHT){

            @Override
            public boolean onMove(RecyclerView rV, RecyclerView.ViewHolder vH, RecyclerView.ViewHolder targ) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder vH, int dir) {
                String msgSwipe = "swiped: ";
                msgSwipe += "Loc= " + vH.getAdapterPosition();
                Toast.makeText(RviewTwoAsyncListActivity.this,
                        msgSwipe, Toast.LENGTH_SHORT).show();
            }
        };
        ItemTouchHelper touchHelper = new ItemTouchHelper(slideCallback);
        touchHelper.attachToRecyclerView(rviewBinding.rvAsyncLayoutTwo);
    }

}
