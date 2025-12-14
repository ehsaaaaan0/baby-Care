package com.babycare.tips.roomDatabase;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.babycare.tips.models.NotesModel;
import com.babycare.tips.models.NotesUserModel;

@androidx.room.Database(entities = {NotesUserModel.class, NotesModel.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract Dao dao();
    private static volatile Database INSTANCE;

    public static Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),Database.class,"Notes_Database").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}

