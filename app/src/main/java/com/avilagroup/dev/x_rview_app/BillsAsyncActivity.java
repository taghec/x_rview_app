package com.avilagroup.dev.x_rview_app;

import android.animation.LayoutTransition;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.avilagroup.dev.x_rview_app.databinding.ActivityBillsAsyncBinding;
import com.avilagroup.dev.x_rview_app.model.BillParsedObs;
import com.avilagroup.dev.x_rview_app.util.HelperBillsCB;
import com.avilagroup.dev.x_rview_app.util.asyncBillsCB;
import com.avilagroup.dev.x_rview_app.util.cvBillAdapter;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BillsAsyncActivity
        extends AppCompatActivity {
    RecyclerView.Adapter rvBillsAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_name) + " - Bills");
//        setContentView(R.layout.activity_bills_async);

        /**
         * BINDING AND LAYOUT MANAGER
         */
        final ActivityBillsAsyncBinding mainBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_bills_async);
        final RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(this);
        mainBinding.rvBillsAsync.setLayoutManager(rvLayoutManager);

        /**
         * ADAPTER, LIST MANAGER, AND ASYNC CALL
         */
        List<BillParsedObs> listBills = new ArrayList<>();
        rvBillsAdapter = new cvBillAdapter(this, listBills);
//        mainBinding.rvBillsAsync.setAdapter(rvBillsAdapter);
        new asyncBillsCB(this, mainBinding, (cvBillAdapter) rvBillsAdapter).execute();

        /**
         * Animation - default implementation
         *
         * Ex from: https://www.sitepoint.com/mastering-complex-lists-with-the-android-recyclerview/
         */
        RecyclerView.ItemAnimator rvAnimator = new DefaultItemAnimator();
//        rvAnimator.setRemoveDuration(1000); // this will slide-remove slower
//        rvAnimator.setAddDuration(1000); // effect only when adding
//        rvAnimator.setChangeDuration(1000);
//        rvAnimator.setMoveDuration(1000);
//        mainBinding.rvBillsAsync.setItemAnimator(rvAnimator);

        /**
         * GESTURE RESPONSE
         *
         * I've created a wrapper for the .SimpleCallback class for both
         * education ;), and clarity.
         *
         * ACTUALLY - this works!! however, since the list is needed for the
         * effect, it needs to be populated first. That means, it is possible
         * the ASYNC call above for the data may not be finished by the time this
         * gets setup, which means the CB here will be setup on an empty set.
         *
         * It is then, necessary to move this assignment into the ASYNC call itself
         * so it is setup only after the data is available (onPostExec).
         */
//        ItemTouchHelper.SimpleCallback slideCB = new HelperBillsCB(this,rvBillsAdapter,listBills);
//        ItemTouchHelper touchHelper = new ItemTouchHelper(slideCB);
//        touchHelper.attachToRecyclerView(mainBinding.rvBillsAsync);
    }

}
