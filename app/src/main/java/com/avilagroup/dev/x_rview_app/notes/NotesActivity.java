package com.avilagroup.dev.x_rview_app.notes;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.avilagroup.dev.x_rview_app.R;
import com.avilagroup.dev.x_rview_app.databinding.ActivityNotesBinding;
import com.avilagroup.dev.x_rview_app.notes.model.NoteThingObs;
import com.avilagroup.dev.x_rview_app.notes.util.NotesAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {
    ActivityNotesBinding notesBinding;
    List<NoteThingObs> listNotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView.Adapter rvAdapter;

        /**
         * Basics
         */
        setTitle("TAG Notes");
        notesBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_notes);
        setSupportActionBar(notesBinding.tbNotes);
        notesBinding.fabNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /**
         * Start the Recycler binding
         *
         * Notice I'm using an 'included' layout for the RV. This is how the
         * template comes for Android basic act.
         */
        final RecyclerView.LayoutManager rvManager = new LinearLayoutManager(this);
        notesBinding.rvlayout.rvNotes.setLayoutManager(rvManager);
        rvAdapter = new NotesAdapter(this, listNotes);
    }

}
