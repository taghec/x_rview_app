package com.avilagroup.dev.x_rview_app.notes.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.avilagroup.dev.x_rview_app.notes.NotesActivity;
import com.avilagroup.dev.x_rview_app.notes.model.NoteThingObs;

import java.util.List;

/**
 * Created by taghec on 10/22/2016.
 */
public class ListHelperCB extends ItemTouchHelper.SimpleCallback {
    Context context;
    RecyclerView.Adapter mAdapter;
    List<NoteThingObs> list;
    private final static String RECS_FILE_LOCAL = "notes.dat";

    public ListHelperCB(NotesActivity context, NotesAdapter adapter, List<NoteThingObs> notes) {
        super(0,ItemTouchHelper.RIGHT);

        this.context = context;
        this.mAdapter = adapter;
        this.list = notes;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder vH, int dir) {
        StorageTools storageTools = new StorageTools(context, RECS_FILE_LOCAL);
        int pos = vH.getAdapterPosition();

        list.remove(pos);
        storageTools.removeRecord(pos);
        mAdapter.notifyItemRemoved(pos);
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder vH) {
        float factor = 1.5f;
        return super.getSwipeThreshold(vH)*factor;
    }
}
