package com.avilagroup.dev.x_rview_app;

import android.animation.LayoutTransition;
import android.content.Intent;
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
import com.avilagroup.dev.x_rview_app.util.StorageTools;
import com.avilagroup.dev.x_rview_app.util.asyncBillsCB;
import com.avilagroup.dev.x_rview_app.util.cvBillAdapter;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BillsAsyncActivity
        extends AppCompatActivity
        implements cvBillAdapter.SelectionCallBack{
    private static final String SAVE_FILE = "bills.json";
    RecyclerView.Adapter rvBillsAdapter;
    List<BillParsedObs> listBills = new ArrayList<>();
    ActivityBillsAsyncBinding mainBinding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.app_name) + " - Bills");
//        setContentView(R.layout.activity_bills_async);

        /**
         * BINDING AND LAYOUT MANAGER
         */
        mainBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_bills_async);
        final RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(this);
        mainBinding.rvBillsAsync.setLayoutManager(rvLayoutManager);

        /**
         * ADAPTER, LIST MANAGER, AND ASYNC CALL
         */
        rvBillsAdapter = new cvBillAdapter(this, listBills);
//        mainBinding.rvBillsAsync.setAdapter(rvBillsAdapter);
        new asyncBillsCB(this, mainBinding, (cvBillAdapter) rvBillsAdapter).execute();

        /**
         * Animation - default implementation
         *
         * Ex from: https://www.sitepoint.com/mastering-complex-lists-with-the-android-recyclerview/
         */
//        RecyclerView.ItemAnimator rvAnimator = new DefaultItemAnimator();
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

    /**
     * Implementation based on description here:
     * http://stackoverflow.com/questions/34340703/super-onactivityresult-inside-adapter-class-android
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        StorageTools storageTools = new StorageTools(this, SAVE_FILE);

        if( requestCode == 1 && resultCode == RESULT_OK) {
//            int loc = data.getIntExtra("listLoc",0);
//            int loc = 2;
//            listBills.set(loc,storageTools.getDevRec(loc));
//            rvBillsAdapter.notifyItemChanged(loc);
/*
            rvBillsAdapter = new cvBillAdapter(BillsAsyncActivity.this, listBills);
            new asyncBillsCB(BillsAsyncActivity.this, mainBinding, (cvBillAdapter) rvBillsAdapter).execute();
            rvBillsAdapter.notifyDataSetChanged();
*/

            recreate();
        }

    }

    @Override
    public void itemSelection(int pos, int size, String msg) {
        Intent actBillBinding = new Intent(this, BillBinding.class);
        actBillBinding.putExtra("listLoc", pos);
        actBillBinding.putExtra("listSize", size);

//        mContext.startActivity(actBillBinding);
        startActivityForResult(actBillBinding, 1);
    }
}
