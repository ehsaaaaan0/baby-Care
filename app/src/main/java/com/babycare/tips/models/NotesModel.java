package com.babycare.tips.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Notes Table")

public class NotesModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    public String title, note;
    public String email;
    public String date;

    public NotesModel(String title, String note, String email, String date) {
        this.title = title;
        this.note = note;
        this.email = email;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

