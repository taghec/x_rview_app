package com.avilagroup.dev.x_rview_app.util;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.avilagroup.dev.x_rview_app.BillsAsyncActivity;
import com.avilagroup.dev.x_rview_app.databinding.ActivityBillsAsyncBinding;
import com.avilagroup.dev.x_rview_app.model.BillParsedObs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Random;

/**
 * Created by taghec on 10/5/2016.
 */
public class asyncBillsCB
        extends AsyncTask<Void, Void, Void>{
    private Context context;
    private final ActivityBillsAsyncBinding binding;
    private cvBillAdapter adapter;
    private List<BillParsedObs> bills;
    private List<String> records_data = new ArrayList<>();
    private final static String RECS_FILE_LOCAL = "bills.json";
    private final static String SAVE_FORMAT = "txt";
    private StorageTools storageTools;

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
        this.storageTools = new StorageTools(context, RECS_FILE_LOCAL);
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
/*
//        FileInputStream iFile;
        try{
            FileReader iFile = new FileReader(devFile);
            records_data = _readLocalRecords(iFile);
//            String filePath = context.getFileStreamPath(json_file).getPath();
//            Log.d("ASYNC CB:","Background process. IO File: " + filePath);
            iFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        records_data = storageTools.getDevRecords();

        /**
         * Got a file, now attempt reading/parsing contents
         *
         * Otherwise, return DEMO data
         */
/*
        try {
            bills = _parseRecords(records_data);

        } catch (IllegalFormatException e) {
            e.printStackTrace();
        }
*/
//        if (!records_data.isEmpty())
            bills = storageTools.parseRecords(records_data);

        /**
         * testing persistence - saving to local list file
         *
         * see https://developer.android.com/training/basics/data-storage/files.html
         */
/*
        try{
//            FileOutputStream oFile;
//            oFile = context.openFileOutput(RECS_FILE_LOCAL,Context.MODE_PRIVATE);
//            records_data.add("txt");   // rec the file format on first row
//            oFile.write(records_data.get(DEMO_I).getBytes());  // firt row is format
//            DEMO_I++;
//            records_data.add("DEMO Data");     // first real rec, demo txt for now
//            oFile.write(records_data.get(DEMO_I).getBytes());  // second row is demo rec
//            oFile.close();
//            FileWriter oFile = new FileWriter(context.getFileStreamPath(RECS_FILE_LOCAL));
//            _storeLocalRecords(oFile, records_data);
//            oFile.close();
////            DEMO_I++;   // we'll shift by one if we'll use demo entry
////            bills.add(new BillParsedObs(json_data,new Random().nextLong()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
*/
        // sending the actual output from parsing will get either records, or DEMO data.
        // On return, we'll have saved something - no longer empty.
        storageTools.saveRecords(bills, SAVE_FORMAT);

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

        /**
         * FAB action - add new Bill (random for now)
         */
        binding.fabBillAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lastBill = adapter.getItemCount();
                bills.add(new BillParsedObs("New Bill",new Random().nextLong()));
                // send the last added to dev storage, offset the zero
                storageTools.saveNewRecord(bills.get(lastBill));
                lastBill++;
                Snackbar.make(v, "Adding new Bill. New total: " + lastBill,
                        Snackbar.LENGTH_SHORT).setAction("Action",null).show();
                adapter.notifyItemInserted(lastBill);

            }
        });
    }


/*
    */
/**
     * Will probably become a class that I can use from other
     * activities. For now, just keep this local to the call so I can
     * store results after the call has been made.
     *
     * @param file      - store txt stream into file
     * @param records   - each record will be saved either in txt (each row a rec),
     *                      or possibly as json data (json).
     * @throws IOException
     *//*

    private void _storeLocalRecords(FileWriter file,
                                    List<String> records)
            throws IOException {
//        String rec;
        // completely demo for now
//        records.clear();
//        records.add("txt\n");
//        records.add("DEMO Data");

        BufferedWriter oBuffer = new BufferedWriter(file);

        oBuffer.write("txt\n");
        for (String rec : records) {
            Log.d("ASYNC CB", "Storing rec: " + rec);
            oBuffer.write(rec + "\n");
        }
        oBuffer.close();
    }
*/

/*
    */
/**
     * _parseRecords - this will take a txt stream, w first row indicating formatting of rest
     * of rows.  Options include txt = each row is a record, json = single row w obj/arry formatted
     * in json to be parsed using gson (possibly).
     *
     * @param rows - string records. first should be single key for file format (txt,json,etc)
     * @return      - List of 'Bills' objs
     * @throws IllegalFormatException
     *//*

    private List<BillParsedObs> _parseRecords(List<String> rows)
            throws IllegalFormatException{
        List<BillParsedObs> records = new ArrayList<>();
        String format = "DEMO Bill";

        if (rows.size()>0) {
            format = rows.get(0);
            rows.remove(0);
        } else {
            // unsupported size, make safe
            rows.clear();
            rows.add(format);
        }

        switch (format) {
            case "txt":
                */
/**
                 * loop to get all recs from list. start from second row
                 * after the 'format' first row - ie: from first rec
                 *//*

                Log.d("ASYNC CB", "Local records found. Total lines = " + rows.size());
*/
/*
                for (int i = 1; i<records_data.size(); i++){
                    bills.add(new BillParsedObs(records_data.get(i), new Random().nextLong()));
                }
*//*

                for (String row : rows) {
                    records.add(new BillParsedObs(row, new Random().nextLong()));
                }
                break;
            case "json":
                Log.d("ASYNC CB", "Local records found. JSON records found.");
                // parse w gson
                //break;
            default:
                // if format not supported, create demo data.
                Log.d("ASYNC CB", "No records found or format (" + format + ") not supported. Returning DEMO");
                records.add(new BillParsedObs("DEMO DATA" + 100, new Random().nextLong()));
                for (int i = 1; i < DEMO_BILLS; i++) {
                    records.add(new BillParsedObs("Bill Name" + 10 + i, (new Random().nextLong())));
                }
        }

        return records;
    }
*/

/*
    */
/**
     * Method to parse local found data. Could be list of rows,
     * or eventually become the json file that would be expected from
     * url call.
     *
     * @param file - file to be attempted to take input from
     * @return      - list of txt rows read from input file
     *//*

    private List<String> _readLocalRecords(FileReader file) {
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

        return records;
    }
*/

}
