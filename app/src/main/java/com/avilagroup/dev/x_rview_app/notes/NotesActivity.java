package com.avilagroup.dev.x_rview_app.notes;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.avilagroup.dev.x_rview_app.R;
import com.avilagroup.dev.x_rview_app.databinding.ActivityNotesBinding;
import com.avilagroup.dev.x_rview_app.notes.model.NoteThingObs;
import com.avilagroup.dev.x_rview_app.notes.util.NotesAdapter;
import com.avilagroup.dev.x_rview_app.notes.util.NotesAsyncCB;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity implements NotesAsyncCB.ListAccessCB{
    ActivityNotesBinding notesBinding;
    RecyclerView.Adapter rvAdapter;
    Menu mMenu;
    MenuItem mMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        /**
         * GET DATA - This is an Async call. Any further use of the result will
         *          need some sort of 'interface' to the async class, or we won't
         *          have the data avail when we think we do.
         */
        new NotesAsyncCB(this,notesBinding,(NotesAdapter) rvAdapter).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mMenuItem = menu.findItem(R.id.menu_notelist_save_opt);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.menu_notelist_save_opt:
                Snackbar.make(notesBinding.getRoot(),"attempting save...",Snackbar.LENGTH_SHORT).show();
                item.setEnabled(false);
                break;
            default:
                Snackbar.make(notesBinding.getRoot(),"attempting save...",Snackbar.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void removeItem(int pos) {
        // don't think I can use this here
    }

    @Override
    public void addItem(String item_name) {
        // don't think I can use this here
    }

    @Override
    public void populateAdapter(List<NoteThingObs> noteList) {
        rvAdapter = new NotesAdapter(this,noteList);
        notesBinding.rvlayout.rvNotes.setAdapter(rvAdapter);
        Log.d("ASYNC CB POST","Notes: " + noteList.size() + "| Adapter: " + rvAdapter.getItemCount());
        rvAdapter.notifyDataSetChanged();

        // enable saving AFTER data is avail
//        mMenu.findItem(R.id.menu_notelist_save_opt).setEnabled(true);
//        mMenuItem.setEnabled(true);
    }

    @Override
    public void saveItemList(List<NoteThingObs> noteList) {

    }
}
