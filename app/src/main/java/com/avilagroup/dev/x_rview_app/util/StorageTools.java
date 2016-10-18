package com.avilagroup.dev.x_rview_app.util;

import android.content.Context;
import android.util.Log;

import com.avilagroup.dev.x_rview_app.model.BillParsedObs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Random;

/**
 * Created by taghec on 17/10/2016.
 */
public class StorageTools {
    private final File devFile;
    Context context;
    private final static int DEMO_RECS = 5;

    public StorageTools(Context context, String localFile) {
        this.context = context;
        this.devFile = context.getFileStreamPath(localFile);
    }


    /**
     * getDevRecords - retrieve locally stored data. This will be a serialized (txt)
     * file defined either by constant here, or passed as parameter on constructor.
     *
     * @return      - txt rows. The first will always be the format of the
     *                  file. txt = rows of data; json = probably single row w json recs.
     *                  There will always be then, at least 1 row (format), and either
     *                  multiple txt rows, no rows (no data), or 2nd row w json recs.
     */
    public List<String> getDevRecords() {
        List<String> records = new ArrayList<>();
        String rec;

        try{
            FileReader iFile = new FileReader(devFile);
            BufferedReader iBuffer = new BufferedReader(iFile);
            while ((rec=iBuffer.readLine()) != null) {
                Log.d("ASYNC CB","Record found. Adding to list: " + rec);
                records.add(rec);
            }
            iBuffer.close();
            iFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }

    /**
     * parseRecords - this will take a txt stream, w first row indicating formatting of rest
     * of rows.  Options include txt = each row is a record, json = single row w obj/arry formatted
     * in json to be parsed using gson (possibly).
     *
     * @param rows - string records. first should be single key for file format (txt,json,etc)
     * @return      - List of 'Bills' objs
     * @throws IllegalFormatException
     */
    public List<BillParsedObs> parseRecords(List<String> rows)
            throws IllegalFormatException {
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
                /**
                 * loop to get all recs from list. start from second row
                 * after the 'format' first row - ie: from first rec
                 */
                Log.d("ASYNC CB", "Local records found. Total lines = " + rows.size());
/*
                for (int i = 1; i<records_data.size(); i++){
                    bills.add(new BillParsedObs(records_data.get(i), new Random().nextLong()));
                }
*/
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
                for (int i = 1; i < DEMO_RECS; i++) {
                    records.add(new BillParsedObs("Bill Name" + 10 + i, (new Random().nextLong())));
                }
        }

        return records;
    }

    /**
     * Will probably become a class that I can use from other
     * activities. For now, just keep this local to the call so I can
     * store results after the call has been made.
     *
     * @param rows   - each record will be saved either in txt (each row a rec),
     *                      or possibly as json data (json).
     */
    public int saveRecords(List<String> rows) {
        String format = "txt\n";
        int saved = 0;

        try{
            BufferedWriter oBuffer = new BufferedWriter(new FileWriter(devFile));
            oBuffer.write(format);
            for (String row : rows) {
                Log.d("ASYNC CB", "Storing rec: " + row);
                oBuffer.write(row + "\n");
            }
            oBuffer.close();
            saved = rows.size();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return saved;
    }

    public void saveRecords(List<BillParsedObs> bills, String format) {
        List<String> records = new ArrayList<>();

        switch (format) {
            case "txt" :
                for (BillParsedObs bill : bills) {
                    records.add(bill.getName());
                }
                saveRecords(records);
                break;
            default:
        }
    }

    public void saveNewRecord(BillParsedObs bill) {
        List<String> existing = getDevRecords();
        List<BillParsedObs> bills = parseRecords(existing);

        bills.add(bill);
        saveRecords(bills, "txt");
    }

    public void removeRecord(int pos) {
        List<String> existing = getDevRecords();
        List<BillParsedObs> bills = parseRecords(existing);

        bills.remove(pos);
        saveRecords(bills, "txt");
    }

    public BillParsedObs getDevRec(int loc) {
        List<BillParsedObs> bills = parseRecords(getDevRecords());

        return bills.get(loc);
    }

}
