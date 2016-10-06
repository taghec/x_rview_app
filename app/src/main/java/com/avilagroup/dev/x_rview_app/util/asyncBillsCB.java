package com.avilagroup.dev.x_rview_app.util;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
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

    /**
     * CONSTRUCTOR
     * @param context
     * @param mainBinding
     * @param rvBillsAdapter
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

        for (int i=0; i<100; i++) {
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
    }
}