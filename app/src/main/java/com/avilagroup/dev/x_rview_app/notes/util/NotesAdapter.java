package com.avilagroup.dev.x_rview_app.notes.util;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.avilagroup.dev.x_rview_app.notes.NotesActivity;
import com.avilagroup.dev.x_rview_app.notes.model.NoteThingObs;

import java.util.List;

/**
 * Created by temp on 19/10/2016.
 */
public class NotesAdapter
        extends RecyclerView.Adapter<NotesAdapter.RecHolder>{
    private Activity activity;
    private List<NoteThingObs> mNotes;
    private SelectionCB selectionCB;

    public NotesAdapter(NotesActivity context, List<NoteThingObs> notes) {
        this.activity = context;
        this.mNotes = notes;
        this.selectionCB = (SelectionCB) context;
    }

    /**
     *  Create an interface to the selected item when items get
     *  bound.
     */
    public interface SelectionCB {
        void itemSelection(int pos, int listSize, String notes);
    }

    @Override
    public RecHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(final RecHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * Define the RecHolder Class
     */
    public class RecHolder extends RecyclerView.ViewHolder {

        public RecHolder(View itemView) {
            super(itemView);
        }
    }
}
