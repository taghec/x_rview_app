package com.avilagroup.dev.x_rview_app.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.avilagroup.dev.x_rview_app.R;
import com.avilagroup.dev.x_rview_app.RviewTwoAsyncListActivity;
import com.avilagroup.dev.x_rview_app.databinding.ActivityRviewTwoAsyncListBinding;
import com.avilagroup.dev.x_rview_app.model.Person;
import com.avilagroup.dev.x_rview_app.model.PersonParsed_Obs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taghec on 28/09/2016.
 */

public class asyncGetPeopleTwo
        extends AsyncTask<Void, Void, Void>{
    // syntax AsyncTask<params,units of progress,result type>
    private ProgressDialog progressDialog;
    private Context context;
    private List<PersonParsed_Obs> mPersons;
    private ActivityRviewTwoAsyncListBinding binding;

    public asyncGetPeopleTwo(RviewTwoAsyncListActivity context,
                             ActivityRviewTwoAsyncListBinding binding) {
        this.context = context;
        this.binding = binding;
    }

    public asyncGetPeopleTwo(Context context, ActivityRviewTwoAsyncListBinding rviewBinding, List<PersonParsed_Obs> mPersons) {
        this.context = context;
        this.binding = rviewBinding;
        this.mPersons = mPersons;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // status message
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources()
                .getString(R.string.stg_inprogress));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Log.d("PARSED: ","Showing progress dialog...");
    }

    @Override
    protected Void doInBackground(Void... params) {
/*
        */
/**
         * I'll skip creating the PersonParsedUtil for now
         *//*

        List<PersonParsed_Obs> mPersons = new ArrayList<>();
        for (int i=0; i<100; i++){
            mPersons.add(new PersonParsed_Obs("Last"+i+1,"First"+i+1));
        }
*/
        final PersonUtilParsed_Obs personUtil = new PersonUtilParsed_Obs();
        this.mPersons = personUtil.getPersons();
        Log.d("PARSED: ","Background call ran");

        return null;
    }

    @Override
    protected void onPostExecute(Void personParsed_obs) {
        super.onPostExecute(personParsed_obs);

        if(progressDialog.isShowing())
            progressDialog.dismiss();
        Log.d("PARSED: ","Dialog message removed");


        /**
         * Attach the binding sent to this Async call.
         */
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        this.binding.rvAsyncLayoutTwo.setLayoutManager(layoutManager);
        Log.d("PARSED: ","View Holder generated");
        /**
         * Attach the adapter for custom class/model. Since this is now 'Observable',
         * the thinking is that if I send changes to the data, the view will
         * automatically adjust accordingly.
         */
        final RecyclerView.Adapter personAdapter = new cvPersonParsedAdapter(this.context,mPersons);
        this.binding.rvAsyncLayoutTwo.setAdapter(personAdapter);

        Log.d("PARSED: ","Data bound to adapter. Items: " + personAdapter.getItemCount());
    }

/*
    @Override
    protected void onPostExecute(List<PersonParsed_Obs> results) {
        super.onPostExecute(results);

        mAdapter = new cvPersonAdapter(context, results);
//        mAdapter.notifyDataSetChanged();

        binding.rvAsyncLayoutTwo.setAdapter(mAdapter);

        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }
*/
}
