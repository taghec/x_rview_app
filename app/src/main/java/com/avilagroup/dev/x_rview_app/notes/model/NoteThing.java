package com.avilagroup.dev.x_rview_app.notes.model;

import com.avilagroup.dev.x_rview_app.model.Thing;

/**
 * Created by temp on 19/10/2016.
 */

public interface NoteThing
        extends Thing {
    long ONE_DAYMIL = 24*60*60*1000;
    String NOTE_STATUS[] = {"active","read","archived"};

    String getStatus();
    long getDateModified();
    String getNoteDetails();

    void setStatus(String status);
    void setDateModified(long date);
    void setNoteDetails(String noteDetails);
}
