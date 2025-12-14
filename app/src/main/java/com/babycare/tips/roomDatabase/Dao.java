package com.babycare.tips.roomDatabase;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.babycare.tips.models.NotesModel;
import com.babycare.tips.models.NotesUserModel;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert
    void insertUser(NotesUserModel model);

    @Update
    void updateUser(NotesUserModel model);

    @Query("DELETE FROM `Users Table`")
    public void deleteAllUsers();

    @Query("SELECT * FROM `Users Table`")
    List<NotesUserModel> getAllUsers();

    @Query("DELETE FROM 'Users Table' WHERE id=:uID")
    void deleteUserById(int uID);



    @Query("SELECT * FROM `Notes Table` WHERE email=:mail")
    List<NotesModel> getAllNotes(String mail);

    @Insert
    void insertNote(NotesModel model);

    @Query("DELETE FROM `Notes Table`")
    public void deleteAllNotes();

    @Query("DELETE FROM `Notes Table` WHERE id=:uID")
    void deleteNoteById(int uID);
}

