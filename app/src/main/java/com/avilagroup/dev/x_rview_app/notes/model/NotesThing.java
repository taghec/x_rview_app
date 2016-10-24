package com.avilagroup.dev.x_rview_app.notes.model;

import com.avilagroup.dev.x_rview_app.model.Thing;

import java.util.List;

/**
 * Created by temp on 24/10/2016.
 */

public interface NotesThing extends Thing {
    String getNotesDesc();
    List<String> getNotesErrors();
    int getNotesTotal();
    int getNotesPages();
    int getNotesLimit();
    long getNotesDateMod();
    List<NoteThingObs> getNoteList();

    void setNotesDesc(String notesDesc);
    void setNotesErrors(List<String> notesErrors);
    void setNotesTotal(int notesTotal);
    void setNotesPages(int notesPages);
    void setNotesLimit(int notesLimit);
    void setNotesDateMod(long notesDateMod);
    void setNoteList(List<NoteThingObs> noteList);
}
