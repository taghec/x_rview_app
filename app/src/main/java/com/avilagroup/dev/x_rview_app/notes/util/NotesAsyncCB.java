package com.avilagroup.dev.x_rview_app.notes.util;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

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
    private ListAccessCB listAccessCB;
    private StorageTools storageTools;
    private MenuItem saveOpt;
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
        this.listAccessCB = context;
        this.storageTools = new StorageTools(context, RECS_FILE_LOCAL);
        this.mNotes = new ArrayList<>();
    }

    /**
     * INTERFACE - Use these methods to gain access to the results.
     *          Notice the populateAdapter method is called here, after
     *          data is retrieved.  Implementing and Overriding the method elsewhere
     *          should give access to that.
     */
    public interface ListAccessCB {
        void removeItem(int pos);
        void addItem(String item_name);
        void populateAdapter(List<NoteThingObs> noteList);
        void saveItemList(List<NoteThingObs> noteList);
    }

    /**
     * ENABLE SAVE - After sync call complete. See:
     * http://stackoverflow.com/questions/13359010/trouble-making-menuitem-visible-after-asynctask
     *
     * Probably don't need this though - see current solution of global MenuItem on
     * Main Act.
     *
     * @param menuItem
     */
    public void MenuSaveOption(MenuItem menuItem) {
        this.saveOpt = menuItem;
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
        this.mNotes = storageTools.parseRecords(_notes, new NoteThingObs("temp"));
//        storageTools.saveRecords(mNotes,SAVE_FORMAT);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        listAccessCB.populateAdapter(mNotes);
//        saveOpt.setEnabled(true);
    }
}
