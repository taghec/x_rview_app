package com.avilagroup.dev.x_rview_app.notes.util;

import android.content.Context;
import android.util.Log;

import com.avilagroup.dev.x_rview_app.notes.NotesActivity;
import com.avilagroup.dev.x_rview_app.notes.model.NoteThingObs;
import com.avilagroup.dev.x_rview_app.notes.model.Notes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

/**
 * Created by taghec on 10/21/2016.
 */
public class StorageTools {
    private final File devFile;
    private final static int DEMO_RECS = 5;
    private final static String FORMAT_TXT = "txt";
    private final static String FORMAT_JSON = "json";

    /**
     * CONSTRUCTOR
     * @param context - needed for priv storage file
     * @param devFile - file to use must be sent
     */
    public StorageTools(Context context, String devFile) {
        this.devFile = context.getFileStreamPath(devFile);
    }

    public List<String> getDevRecords(){
        List<String> records = new ArrayList<>();
        String rec;

        try{
            BufferedReader iBuffer = new BufferedReader(new FileReader(devFile));
            while ((rec=iBuffer.readLine()) != null){
                records.add(rec);
            }
            iBuffer.close();
            Log.d("NOTES STORAGE DEV",
                    "Records found: " + String.format("%1$s", records.size()-1));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }
    public List<NoteThingObs> getParsedRecords(){
        return getParsedRecords(getDevRecords());
    }
    public List<NoteThingObs> getParsedRecords(List<String> parse_rows) {
        String format = "DEMO Note";
        List<NoteThingObs> rec = new ArrayList<>();
        Notes notes = new Notes();

        if (parse_rows.isEmpty()) {
            //nothing given, send demo
            parse_rows.add(format);
        } else {
            //store format header, then remove it from data
            format = parse_rows.get(0);
            parse_rows.remove(0);
        }

        /**
         * PARSING - Depending on what's found on the header 'format', parse
         *          in different ways.
         */
        switch (format) {
            case FORMAT_TXT:
                for (String row : parse_rows)
                    rec.add(new NoteThingObs(row));
                break;
            case FORMAT_JSON:
                if(!parse_rows.isEmpty()) {
                    Gson gson = new Gson();
                    notes = gson.fromJson(parse_rows.get(0),Notes.class);
                    rec = notes.getNoteList();
                }
                break;
            default:
                Log.d("NOTES STORAGE PARSE","No records found. Format attempted: " + format);
                rec.add(new NoteThingObs("DEMO DATA" + 100));
                for (int i =1; i<DEMO_RECS; i++)
                    rec.add(new NoteThingObs(String.format("DEMO Note" + 10+i)));
        }

        Log.d("NOTES STORAGE PARSE","Records processed: " + rec.size());
        return rec;
    }

    public void saveRecords(List<NoteThingObs> notes, String format) {
        List<String> recs;
        switch (format) {
            case FORMAT_TXT:
                recs = new ArrayList<>();
                for (NoteThingObs note : notes)
                    recs.add(note.getName());
//                saveRecords(recs);
//                break;
            case FORMAT_JSON:
                recs = new ArrayList<>();
                Notes notes_obj = new Notes();
                notes_obj.setNoteList(notes);
                notes_obj.setNotesTotal(notes.size());

                GsonBuilder builder = new GsonBuilder();
                builder.serializeNulls();
                Gson gson = builder.create();
                String json_stg = gson.toJson(notes_obj);

//                Log.d("NOTES STORAGE","Storing JSON: " + json_stg);
                recs.add(json_stg);
                saveRecords(recs);
                break;
            default:
        }
    }

    private void saveRecords(List<String> rows) {
//        String format = FORMAT_TXT + "\n";
        String format = FORMAT_JSON + "\n";
        int recs = 0;

        try{
            BufferedWriter oBuffer = new BufferedWriter(new FileWriter(devFile));
            oBuffer.write(format);
            for (String row : rows) {
                oBuffer.write(row + "\n");
                recs++;
            }
            oBuffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("NOTES STORAGE SAVING", "Saved recs: " + recs);
    }

    public NoteThingObs getDevRec(int loc) {
        List<NoteThingObs> recs = getParsedRecords(getDevRecords());

        return recs.get(loc);
    }

    public void updateRecord(NoteThingObs newNote, int note_id) {
        List<NoteThingObs> notes = getParsedRecords(getDevRecords());
        NoteThingObs oldNote;

/*
        if ((oldNote=notes.get(note_id))!=null) // make sure it 'exists' to update
            if (newNote.getId()==oldNote.getId()) // found id @ loc, so we can update
                notes.set(note_id,newNote);
        else notes.add(newNote);
*/
        notes.set(note_id, newNote);

        saveRecords(notes,FORMAT_JSON);
    }

    public void saveNewRec(NoteThingObs note) {
        List<NoteThingObs> notes = getParsedRecords();
        notes.add(note);
        saveRecords(notes,FORMAT_JSON);
    }

    public void removeRecord(int loc) {
        List<NoteThingObs> notes = getParsedRecords();

        notes.remove(loc);
        saveRecords(notes,FORMAT_JSON);
    }
}
