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
    private long createDate;
    private String noteStatus;
    private long modDate;

    /**
     * CREATORS
     *
     * @param noteName - the name of the note to use
     */
    public NoteThingObs(String noteName) {
        this.noteName = noteName;

        this.id = new Random().nextInt();
        this.noteTangible = 0;
        this.createDate = new Date().getTime();
        this.modDate = createDate;
        this.noteStatus = NOTE_STATUS[0];
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
        return createDate;
    }

    @Override
    @Bindable
    public String getStatus() {
        return noteStatus;
    }

    @Override
    @Bindable
    public long getDateModified() {
        return modDate;
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
        this.createDate = date;
        notifyPropertyChanged(BR.createdDate);
    }

    @Override
    public void setStatus(String status) {
        this.noteStatus = status;
        notifyPropertyChanged(BR.status);
    }

    @Override
    public void setDateModified(long date) {
        this.modDate = date;
        notifyPropertyChanged(BR.dateModified);
    }
}
