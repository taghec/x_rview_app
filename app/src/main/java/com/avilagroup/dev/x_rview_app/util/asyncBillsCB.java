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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
        this.bills = new ArrayList<>();
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
//        this.bills = new ArrayList<>();
        int DEMO_I = 0;
        String json_data = "DEMO data";
        String json_file = "bills.json";
        boolean haveLocalList = false;

        /**
         * testing persistence
         *
         * see https://developer.android.com/training/basics/data-storage/files.html
         */
/*
        FileOutputStream oFile;
        try{
            oFile = context.openFileOutput(json_file,Context.MODE_PRIVATE);
            oFile.write(json_data.getBytes());
            oFile.close();
*/
/*
            DEMO_I++;   // we'll shift by one if we'll use demo entry
            bills.add(new BillParsedObs(json_data,new Random().nextLong()));
*//*

        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        /**
         * try just FileInputStream. saving should happen somewhere else
         * this is more for img readers (byte reader). For txt/chr files,
         * consider using FileReader
         *
         *  now using it.  see: http://alvinalexander.com/blog/post/java/how-open-read-file-java-string-array-list
         *
         *  For a list of lines, create List<> and each line is a 'record' to create an obj for.
         *
         *  Notice this will assume the file already exists, which means it should be
         *  saved/recorded by a different step. This method is only data-getter
         *
         *  Need to get the abs path to the file before calling FileReader, otherwise it'll assume
         *  a file on root path.
         */
//        FileInputStream iFile;
        try{
            FileReader iFile = new FileReader(context.getFileStreamPath(json_file));
//            String filePath = context.getFileStreamPath(json_file).getPath();
//            Log.d("ASYNC CB:","Background process. IO File: " + filePath);
            BufferedReader iBuffer = new BufferedReader(iFile);
            json_data = iBuffer.readLine();
            iBuffer.close();
            iFile.close();
            haveLocalList = !haveLocalList;  // set to true
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (haveLocalList) {
            DEMO_I++;
            // for a read list, loop until we've got all lines in. for now, just single line
            bills.add(new BillParsedObs(json_data, new Random().nextLong()));
        }

        /**
         * If there is no existing list, then create demo data
         */
        for (int i = DEMO_I; i< DEMO_BILLS; i++) {
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
