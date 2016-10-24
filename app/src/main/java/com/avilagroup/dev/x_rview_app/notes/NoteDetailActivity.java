package com.avilagroup.dev.x_rview_app.notes;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.avilagroup.dev.x_rview_app.R;
import com.avilagroup.dev.x_rview_app.databinding.ActivityNoteDetailBinding;
import com.avilagroup.dev.x_rview_app.databinding.ContentNoteDetailBinding;
import com.avilagroup.dev.x_rview_app.notes.model.NoteThingObs;
import com.avilagroup.dev.x_rview_app.notes.util.StorageTools;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

public class NoteDetailActivity extends AppCompatActivity {
    private static final String RECS_FILE_LOCAL = "notes.dat";
    ActivityNoteDetailBinding noteDetailBinding;
    final private static String ITEM_ID = "note_id";
    final private static String LIST_SIZE = "notes_size";
    int note_id, notes_size;
    Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noteDetailBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_note_detail);
        setSupportActionBar(noteDetailBinding.tbNoteDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        note_id = this.getIntent().getIntExtra(ITEM_ID, -1);
        notes_size = this.getIntent().getIntExtra(LIST_SIZE, 0);
        final DateFormat dateFormat = DateFormat.getDateInstance();

        /**
         * Get the note and bind to it.
         */
        NoteThingObs mNote = _getNote(note_id, notes_size);
        noteDetailBinding.loDetail.setNote(mNote);
        String mNoteDate = dateFormat.format(mNote.getCreatedDate());
        String mNoteModDate = dateFormat.format(mNote.getDateModified());
        mNote.setStatus(NoteThingObs.NOTE_STATUS[1]);
        //Now binding
        noteDetailBinding.loDetail.setNote(mNote);
        noteDetailBinding.loDetail.setVariable(BR.stg_note_date, mNoteDate);
        noteDetailBinding.loDetail.setVariable(BR.stg_notemod_date, mNoteModDate);
        noteDetailBinding.loDetail.executePendingBindings();

        /**
         * NAV - activate
         */
        _activateNAV(noteDetailBinding, note_id, notes_size);
    }

    /**
     * OVERRIDES
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_note_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.menu_note_edit)
            _enableEditing(true); else
        if (id==R.id.menu_note_save)
            _enableEditing(false);

        return super.onOptionsItemSelected(item);
    }

    private void _enableEditing(boolean onEdit) {
        ContentNoteDetailBinding binding = noteDetailBinding.loDetail;
        mMenu.findItem(R.id.menu_note_edit).setVisible(!onEdit);
        mMenu.findItem(R.id.menu_note_save).setVisible(onEdit);
        binding.setVariable(BR.editMode, onEdit);

        if(onEdit){
            // turn off NAV
            binding.ibNotePrev.setEnabled(false);
            binding.ibNoteNext.setEnabled(false);
            binding.etNoteDetails.setText(binding.tvNoteDetails.getText());
        } else {
            _activateNAV(noteDetailBinding, note_id, notes_size);
            _recModified();
        }
    }

    /** ----------------------
     * METHODS SECTION
     *
     */

    private void _recModified() {
        NoteThingObs newNote = _getNote(note_id, notes_size);
        boolean toSave = false;
        ContentNoteDetailBinding binding = noteDetailBinding.loDetail;
        DateFormat dateFormat = DateFormat.getDateInstance();
        String newName = binding.etNoteName.getText().toString();
        String newDetail = binding.etNoteDetails.getText().toString();

        if (newName.length()>0) {
            toSave = true;
            newNote.setName(newName);
        }
        if (newDetail.length()>0){
            toSave = true;
            newNote.setNoteDetails(newDetail);
        }

        if (toSave) {
            newNote.setDateModified(new Date().getTime());
            binding.setNote(newNote);
            binding.setVariable(BR.stg_notemod_date,dateFormat
                                            .format(newNote.getDateModified()));
            binding.executePendingBindings();

            StorageTools storageTools = new StorageTools(this,RECS_FILE_LOCAL);
            storageTools.updateRecord(newNote,note_id);
        }

        // reset entry fields
        binding.etNoteName.setText("");
        binding.etNoteDetails.setText("");

        // complete. exit detail view mode. we'll customize exit.
        finish();
    }

    private void _activateNAV(final ActivityNoteDetailBinding binding, int loc, int size) {
        boolean btnP, btnN;
        btnP = btnN = false;
        if (size>0 && (loc+1<=size || loc>=0)) {
            btnP = btnN = true;  // inside the range
            if(loc<=0)
                btnP = false; // top of list
            if(loc+1>=size)
                btnN = false; // bottom of list
        }

        binding.loDetail.ibNotePrev.setEnabled(btnP);
        binding.loDetail.ibNoteNext.setEnabled(btnN);

        binding.loDetail.ibNotePrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note_id--;
                binding.loDetail.setNote(_getNote(note_id,notes_size));
                binding.loDetail.executePendingBindings();
                _activateNAV(binding,note_id,notes_size);
            }
        });
        binding.loDetail.ibNoteNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note_id++;
                binding.loDetail.setNote(_getNote(note_id,notes_size));
                binding.loDetail.executePendingBindings();
                _activateNAV(binding,note_id,notes_size);
            }
        });
    }

    private NoteThingObs _getNote(int note_id, int size) {
        StorageTools storageTools = new StorageTools(this, RECS_FILE_LOCAL);
        int loc = note_id<0 && size>0 ? new Random().nextInt(size-1) : note_id;

        return storageTools.getDevRec(loc);
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra(ITEM_ID, note_id);
        setResult(RESULT_OK,intent);
        super.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("NOTES DETAIL", "Running onPause()...");
    }
}
