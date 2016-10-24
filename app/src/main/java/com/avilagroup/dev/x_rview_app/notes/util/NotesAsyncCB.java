package com.avilagroup.dev.x_rview_app.notes.util;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.avilagroup.dev.x_rview_app.databinding.ActivityNotesBinding;
import com.avilagroup.dev.x_rview_app.notes.NotesActivity;
import com.avilagroup.dev.x_rview_app.notes.model.NoteThingObs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taghec on 20/10/2016.
 */
public class NotesAsyncCB extends AsyncTask<Void,Void,Void>{
    private Activity context;
    private ActivityNotesBinding binding;
    private NotesAdapter mAdapter;
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
        this.binding = binding;
        this.mAdapter = adapter;
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
        void populateAdapter(List<NoteThingObs> noteList);
        void alertNewItemAdapter(int loc);
        void attachListHelper(List<NoteThingObs> mNotes);
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
        this.mNotes = storageTools.getParsedRecords(_notes);
//        storageTools.saveRecords(mNotes,SAVE_FORMAT);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        /**
         * Attach the results
         */
        listAccessCB.populateAdapter(mNotes);
/*
        mAdapter = new NotesAdapter((NotesActivity)context,mNotes);
        binding.rvlayout.rvNotes.setAdapter(mAdapter);
        Log.d("ASYNC CB POST","Notes: " + mNotes.size() + "| Adapter: " + mAdapter.getItemCount());
        mAdapter.notifyDataSetChanged();
*/


        /**
         * Store the results
         */
        storageTools.saveRecords(mNotes,SAVE_FORMAT);
//        saveOpt.setEnabled(true);

        /**
         * Attach FAB action after we know the list
         */
        binding.fabNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lastRec = mNotes.size();
                mNotes.add(new NoteThingObs("New Note"));
                storageTools.saveNewRec(mNotes.get(lastRec));
                lastRec++;

                listAccessCB.alertNewItemAdapter(lastRec);
//                mAdapter.notifyItemInserted(lastRec);
            }
        });

        /**
         * GESTURE HELPER CALL BACK
         */
        listAccessCB.attachListHelper(mNotes);
/*
        ItemTouchHelper.SimpleCallback slideCB = new ListHelperCB((NotesActivity) context, mAdapter, mNotes);
        ItemTouchHelper listHelper = new ItemTouchHelper(slideCB);
        listHelper.attachToRecyclerView(binding.rvlayout.rvNotes);
*/

    }
}
