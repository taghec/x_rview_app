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
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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
    private List<String> records_data = new ArrayList<>();
    final static private int DEMO_BILLS = 20;
    private final static String RECS_FILE_LOCAL = "bills.json";

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
            FileReader iFile = new FileReader(context.getFileStreamPath(RECS_FILE_LOCAL));
            records_data = _parseLocalRecords(iFile);
//            String filePath = context.getFileStreamPath(json_file).getPath();
//            Log.d("ASYNC CB:","Background process. IO File: " + filePath);
            iFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (records_data.size()>1) {
            /**
             * loop to get all recs from list. start from second row
             * after the 'format' first row - ie: from first rec
             */
            Log.d("ASYNC CB","Local records found. Total lines = " + records_data.size());
            for (int i = 1; i<records_data.size(); i++){
                bills.add(new BillParsedObs(records_data.get(i), new Random().nextLong()));
            }
        } else {
            /**
             * haveLocalList = false, then demo data
             */
            Log.d("ASYNC CB","No local records found. Creating DEMO data.");
            bills.add(new BillParsedObs("DEMO DATA"+100,new Random().nextLong()));
            for (int i = 1; i< DEMO_BILLS; i++) {
                bills.add(new BillParsedObs("Bill Name"+10+i,(new Random().nextLong())));
            }
        }

        /**
         * testing persistence - saving to local list file
         *
         * see https://developer.android.com/training/basics/data-storage/files.html
         */
        try{
/*
            FileOutputStream oFile;
            oFile = context.openFileOutput(RECS_FILE_LOCAL,Context.MODE_PRIVATE);
            records_data.add("txt");   // rec the file format on first row
            oFile.write(records_data.get(DEMO_I).getBytes());  // firt row is format
            DEMO_I++;
            records_data.add("DEMO Data");     // first real rec, demo txt for now
            oFile.write(records_data.get(DEMO_I).getBytes());  // second row is demo rec
            oFile.close();
*/
            FileWriter oFile = new FileWriter(context.getFileStreamPath(RECS_FILE_LOCAL));
            _storeLocalRecords(oFile, records_data);
            oFile.close();
//            DEMO_I++;   // we'll shift by one if we'll use demo entry
//            bills.add(new BillParsedObs(json_data,new Random().nextLong()));
        } catch (Exception e) {
            e.printStackTrace();
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



    /**
     * Will probably become a class that I can use from other
     * activities. For now, just keep this local to the call so I can
     * store results after the call has been made.
     *
     * @param file
     * @param records
     * @throws IOException
     */
    private void _storeLocalRecords(FileWriter file,
                                    List<String> records)
            throws IOException {
        String rec;
        // completely demo for now
        records.clear();
        records.add("txt\n");
        records.add("DEMO Data");

        BufferedWriter oBuffer = new BufferedWriter(file);
        for (int i = 0; i< records.size(); i++){
            rec = records.get(i);
            Log.d("ASYNC CB", "Storing rec: " + rec);
            oBuffer.write(rec);
        }
        oBuffer.close();
    }

    /**
     * Method to parse local found data. Could be list of rows,
     * or eventually become the json file that would be expected from
     * url call.
     *
     * @param file
     * @return
     */
    private List<String> _parseLocalRecords(FileReader file) {
        List<String> records = new ArrayList<>();
        String newRec;

        try {
            BufferedReader iBuffer = new BufferedReader(file);
            while ((newRec=iBuffer.readLine()) != null){
                Log.d("ASYNC CB","Record found. Adding to list: " + newRec);
                records.add(newRec);
            }
            iBuffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (records.get(0)){
            case "txt":
                // just remove format, and return demo lines of txt list
//                records.remove(0);    // don't remove the line, need it next time
            case "json":
                // parse the single json line of rec's
                // this will eventually return the Bills list
        }
        return records;
    }
}
