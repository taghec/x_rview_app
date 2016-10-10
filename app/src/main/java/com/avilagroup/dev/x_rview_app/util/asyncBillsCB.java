package com.avilagroup.dev.x_rview_app.util;

import android.animation.LayoutTransition;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.avilagroup.dev.x_rview_app.BillsAsyncActivity;
import com.avilagroup.dev.x_rview_app.databinding.ActivityBillsAsyncBinding;
import com.avilagroup.dev.x_rview_app.model.BillParsedObs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by taghec on 10/5/2016.
 */
public class asyncBillsCB
        extends AsyncTask<Void, Void, Void>{
    private final ActivityBillsAsyncBinding binding;
    private cvBillAdapter adapter;
    private Context context;
    private List<BillParsedObs> bills;
    final static private int DEMO_BILLS = 20;

    /**
     * CONSTRUCTOR
     */
    public asyncBillsCB(BillsAsyncActivity context,
                        ActivityBillsAsyncBinding mainBinding,
                        cvBillAdapter rvBillsAdapter) {
        this.context = context;
        this.binding = mainBinding;
        this.adapter = rvBillsAdapter;
    }

    /**
     * Async call overwrites
     *
     * onPreExecute = optional
     * doInBackground = must implement
     * onPostExecute = optional. Runs in UI - no other way.
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        this.bills = new ArrayList<>();

        for (int i = 0; i< DEMO_BILLS; i++) {
            bills.add(new BillParsedObs("Bill Name"+10+i,(new Random().nextLong())));
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        this.adapter = new cvBillAdapter(context, bills);
        binding.rvBillsAsync.setAdapter(adapter);
        Log.d("BILLS:"," PostExec CB - bills: " + bills.size() + " adapter: " + adapter.getItemCount());
        adapter.notifyDataSetChanged();

        /**
         * This works - see notes in main act
         * However, it does not seem to create the effect desired. It animates the removal,
         * but moving the list in a controled fashion does not work. Maybe it's the
         * LayoutAnimator that needs to be added?
         */
//        RecyclerView.ItemAnimator rvAnimator = new DefaultItemAnimator();
//        rvAnimator.setRemoveDuration(1000); // this will slide-remove slower
//        rvAnimator.setAddDuration(1000); // effect only when adding
//        rvAnimator.setChangeDuration(1000);
//        rvAnimator.setMoveDuration(1000);
//        binding.rvBillsAsync.setItemAnimator(rvAnimator);


        ItemTouchHelper.SimpleCallback slideCB = new HelperBillsCB((BillsAsyncActivity) context,adapter,bills);
        ItemTouchHelper touchHelper = new ItemTouchHelper(slideCB);
        touchHelper.attachToRecyclerView(binding.rvBillsAsync);

//        LayoutTransition rvTransition = new LayoutTransition();
//        binding.rvBillsAsync.setLayoutTransition(rvTransition);
    }
}
