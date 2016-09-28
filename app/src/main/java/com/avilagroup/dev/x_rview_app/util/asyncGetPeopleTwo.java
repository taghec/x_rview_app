package com.avilagroup.dev.x_rview_app.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.avilagroup.dev.x_rview_app.R;
import com.avilagroup.dev.x_rview_app.databinding.ActivityRviewTwoAsyncListBinding;
import com.avilagroup.dev.x_rview_app.model.Person;

import java.util.List;

/**
 * Created by taghec on 28/09/2016.
 */

public class asyncGetPeopleTwo
        extends AsyncTask<Void, Void, List<Person>>{
    // syntax AsyncTask<params,units of progress,result type>
    private ProgressDialog progressDialog;
    private Context context;
    private List<Person> mPersons;
    private RecyclerView.Adapter mAdapter;
    private ActivityRviewTwoAsyncListBinding binding;

    public asyncGetPeopleTwo(Context context, RecyclerView.Adapter adapter, List<Person> personList) {
        this.context = context;
        this.mAdapter = adapter;
        this.mPersons = personList;
    }

    public asyncGetPeopleTwo(Context context, ActivityRviewTwoAsyncListBinding rviewBinding, List<Person> mPersons) {
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
    protected List<Person> doInBackground(Void... params) {
        /**
         * More details on PersonUtil class in the
         * first asyncGetPeople class
         */
        final PersonUtil personUtil = new PersonUtil();
        mPersons = personUtil.getPersons();

        return mPersons;
    }

    @Override
    protected void onPostExecute(List<Person> results) {
        super.onPostExecute(results);

        mAdapter = new cvPersonAdapter(context, results);
//        mAdapter.notifyDataSetChanged();

        binding.rvAsyncLayoutTwo.setAdapter(mAdapter);

        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
