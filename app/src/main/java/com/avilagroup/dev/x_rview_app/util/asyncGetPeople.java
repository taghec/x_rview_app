package com.avilagroup.dev.x_rview_app.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.avilagroup.dev.x_rview_app.R;
import com.avilagroup.dev.x_rview_app.RviewAsyncListActivity;
import com.avilagroup.dev.x_rview_app.databinding.ActivityRviewAsyncListBinding;
import com.avilagroup.dev.x_rview_app.model.Person;

import java.util.List;

/**
 * Created by taghec on 9/23/2016.
 */
public class asyncGetPeople extends AsyncTask<Void, Void, Void>{
    private ActivityRviewAsyncListBinding binding;
    private ProgressDialog progressDialog;
    private Context context;
    private List<Person> persons;
    // two parameter constructor. Since we're called async, we'll attach
    // to the layout manager, and plug the adapter from here.
    public asyncGetPeople(RviewAsyncListActivity context,
                          ActivityRviewAsyncListBinding binding) {
        /* Notice 2nd parameter is the magic class created by
         * the use of the new Android DataBind features.
         * This class is now specifically created for the calling
         * class named under this (modified) name.
        */
        this.context = context;
        this.binding = binding;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // Progress message
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage(this.context.getResources()
                .getString(R.string.stg_inprogress));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        // For now, a static list. Will implement
        // an http call later from this Util class.
        this.persons = PersonUtil.getPersons();
        return null;
    }

    @Override
    protected void onPostExecute(Void results) {
        super.onPostExecute(results);

        // Drop the status message
        if (progressDialog.isShowing())
            progressDialog.dismiss();

        // Attach layout/adapterManagers, and push data.
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.context);
        this.binding.rvRviewList.setLayoutManager(layoutManager);

        // cvPersonAdapter and PersonAdapter are essentially the same,
        // except the former makes reference to the 'ListLayout' for item rows.
        // It is as easy as alternating the name here to get a different layout.
        final RecyclerView.Adapter personsAdapter = new cvPersonAdapter(this.persons);
        this.binding.rvRviewList.setAdapter(personsAdapter);
    }
}
