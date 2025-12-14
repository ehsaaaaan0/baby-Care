package com.babycare.tips.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Users Table")
public class NotesUserModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    public String email, fName, lName,password;

    public NotesUserModel(String email, String fName, String lName, String password) {
        this.email = email;
        this.fName = fName;
        this.lName = lName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

