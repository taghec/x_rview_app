package com.avilagroup.dev.x_rview_app.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.avilagroup.dev.x_rview_app.R;
import com.avilagroup.dev.x_rview_app.databinding.ActivityRviewTwoAsyncListBinding;
import com.avilagroup.dev.x_rview_app.model.Person;
import com.avilagroup.dev.x_rview_app.model.PersonParsed_Obs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taghec on 28/09/2016.
 */

public class asyncGetPeopleTwo
        extends AsyncTask<Void, Void, List<PersonParsed_Obs>>{
    // syntax AsyncTask<params,units of progress,result type>
    private ProgressDialog progressDialog;
    private Context context;
    private List<PersonParsed_Obs> mPersons;
    private RecyclerView.Adapter mAdapter;
    private ActivityRviewTwoAsyncListBinding binding;

    public asyncGetPeopleTwo(Context context, RecyclerView.Adapter adapter, List<PersonParsed_Obs> personList) {
        this.context = context;
        this.mAdapter = adapter;
        this.mPersons = personList;
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
    }

    @Override
    protected List<PersonParsed_Obs> doInBackground(Void... params) {
        /**
         * I'll skip creating the PersonParsedUtil for now
         */
        List<PersonParsed_Obs> mPersons = new ArrayList<>();
        for (int i=0; i<100; i++){
            mPersons.add(new PersonParsed_Obs("Last"+i+1,"First"+i+1));
        }

        return mPersons;
    }

    @Override
    protected void onPostExecute(List<PersonParsed_Obs> personParsed_obses) {
        super.onPostExecute(personParsed_obses);

        if(progressDialog.isShowing())
            progressDialog.dismiss();

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
