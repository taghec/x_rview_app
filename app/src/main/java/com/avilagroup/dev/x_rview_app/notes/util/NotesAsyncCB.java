package com.avilagroup.dev.x_rview_app.notes.util;

import android.app.Activity;
import android.os.AsyncTask;

import com.avilagroup.dev.x_rview_app.databinding.ActivityNotesBinding;
import com.avilagroup.dev.x_rview_app.notes.NotesActivity;
import com.avilagroup.dev.x_rview_app.notes.model.NoteThingObs;
import com.avilagroup.dev.x_rview_app.util.StorageTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taghec on 20/10/2016.
 */
public class NotesAsyncCB extends AsyncTask<Void,Void,Void>{
    private Activity context;
    private List<NoteThingObs> mNotes;
    private StorageTools storageTools;
    private final static String RECS_FILE_LOCAL = "notes.dat";
    private static final String SAVE_FORMAT = "txt";

    /**
     * CONSTRUCTOR
     * @param context   - Calling activity
     * @param binding   - The layout Data binding in use. Used to attach data. Should remove
     *                  after a proper interface is created.
     * @param adapter   - The adapter managing the RV. Should remove after a proper interface
     *                  is created.
     */
    public NotesAsyncCB(NotesActivity context, ActivityNotesBinding binding, NotesAdapter adapter) {
        this.context = context;
        this.storageTools = new StorageTools(context, RECS_FILE_LOCAL);
    }

    public interface ModifyListCB {
        void removeItem(int pos);
        void addItem(String item_name);
    }

    /**
     * Not doing prep 'preExecute' for async, but included here for completion.
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        /**
         * DATA COLLECTION      - Leave the details to the storage class.
         */
        List<String> _notes = storageTools.getDevRecords();
        mNotes = storageTools.parseRecords(_notes, new NoteThingObs("temp"));
//        storageTools.saveRecords(mNotes,SAVE_FORMAT);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
