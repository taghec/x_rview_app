package com.avilagroup.dev.x_rview_app.notes.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.avilagroup.dev.x_rview_app.BR;

import java.util.Date;
import java.util.Random;


/**
 * Created by taghec on 19/10/2016.
 */

public class NoteThingObs
        extends BaseObservable
        implements NoteThing {

    private int id;
    private String noteName;
    private short noteTangible;
    private long dateNote;
    private long dateMod;
    private long dateRead;
    private String noteStatus;
    private String noteDetails;

    /**
     * CREATORS
     *
     * @param noteName - the name of the note to use
     */
    public NoteThingObs(String noteName) {
        this.id = new Random().nextInt();
        this.noteName = noteName;
        this.noteTangible = 1;
        this.dateNote = new Date().getTime();
        this.dateMod = dateNote;
        this.dateRead = this.dateNote;
        this.noteStatus = NOTE_STATUS[0];
        this.noteDetails = "";
    }

    /**
     * GETTERS/SETTERS
     *
     * @return
     */
    @Override
    public int getId() {
        return id;
    }

    @Override
    @Bindable
    public String getName() {
        return noteName;
    }

    @Override
    public short getTangible() {
        return noteTangible;
    }

    @Override
    @Bindable
    public long getCreatedDate() {
        return dateNote;
    }

    @Override
    @Bindable
    public String getStatus() {
        return noteStatus;
    }

    @Override
    @Bindable
    public long getDateModified() {
        return dateMod;
    }

    @Override
    @Bindable
    public long getDateRead() {
        return this.dateRead;
    }

    @Override
    @Bindable
    public String getNoteDetails() {
        return this.noteDetails;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.noteName = name;
        notifyPropertyChanged(BR.name);
    }

    @Override
    public void setTangible(short tangible) {
        this.noteTangible = tangible;
    }

    @Override
    public void setCreatedDate(long date) {
        this.dateNote = date;
        notifyPropertyChanged(BR.createdDate);
    }

    @Override
    public void setStatus(String status) {
        this.noteStatus = status;
        notifyPropertyChanged(BR.status);
    }

    @Override
    public void setDateModified(long dateMod) {
        this.dateMod = dateMod;
        notifyPropertyChanged(BR.dateModified);
    }

    @Override
    public void setDateRead(long dateRead) {
        this.dateRead = dateRead;
        notifyPropertyChanged(BR.dateRead);
    }

    @Override
    public void setNoteDetails(String noteDetails) {
        this.noteDetails = noteDetails;
        notifyPropertyChanged(BR.noteDetails);
    }
}
