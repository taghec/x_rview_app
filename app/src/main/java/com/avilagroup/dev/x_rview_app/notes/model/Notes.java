package com.avilagroup.dev.x_rview_app.notes.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by temp on 24/10/2016.
 */

public class Notes implements NotesThing {
    private int id;
    private String title;
    private List<String> notes_errors;
    private String notes_desc;
    private int notes_total;
    private int notes_pages;
    private int notes_page;
    private int notes_limit;
    private long notes_date;
    private long notes_date_mod;
    private static final String NOTES_TITLE = "Notes Collection";
    private static final String NOTES_DESC = "Collection of Notes for Testing";
    private short tangible;
    private List<NoteThingObs> note_list;

    public Notes() {
        this.id = new Random().nextInt();
        this.title = NOTES_TITLE;
        this.notes_errors = new ArrayList<>();
        this.notes_desc = NOTES_DESC;
        this.notes_total = 0;
        this.notes_pages = 0;
        this.notes_page = 1;
        this.notes_limit = 100;
        this.notes_date = new Date().getTime();
        this.notes_date_mod = this.notes_date;
        this.tangible = 0;
        this.note_list = new ArrayList<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public short getTangible() {
        return tangible; //this collection isn't tangible
    }

    @Override
    public long getCreatedDate() {
        return notes_date;
    }

    @Override
    public String getNotesDesc() {
        return notes_desc;
    }

    @Override
    public List<String> getNotesErrors() {
        return notes_errors;
    }

    @Override
    public int getNotesTotal() {
        return notes_total;
    }

    @Override
    public int getNotesPages() {
        return notes_pages;
    }

    @Override
    public int getNotesLimit() {
        return notes_limit;
    }

    @Override
    public long getNotesDateMod() {
        return notes_date_mod;
    }

    @Override
    public List<NoteThingObs> getNoteList() {
        return this.note_list;
    }

    @Override
    public void setNotesDesc(String notesDesc) {
        this.notes_desc = notesDesc;
    }

    @Override
    public void setNotesErrors(List<String> notesErrors) {
        this.notes_errors = notesErrors;
    }

    @Override
    public void setNotesTotal(int notesTotal) {
        this.notes_total = notesTotal;
    }

    @Override
    public void setNotesPages(int notesPages) {
        this.notes_pages = notesPages;
    }

    @Override
    public void setNotesLimit(int notesLimit) {
        this.notes_limit = notesLimit;
    }

    @Override
    public void setNotesDateMod(long notesDateMod) {
        this.notes_date_mod = notesDateMod;
    }

    @Override
    public void setNoteList(List<NoteThingObs> noteList) {
        this.note_list = noteList;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.title = name;
    }

    @Override
    public void setTangible(short tangible) {
        this.tangible = tangible;
    }

    @Override
    public void setCreatedDate(long date) {
        this.notes_date = date;
    }
}
