package com.dhananjay.lab6;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by dhananjay on 20/2/18.
 */

@Database(entities = { Todo.class }, version = 1)
public abstract class TodoDatabase extends RoomDatabase {

    private static final String DB_NAME = "todoDatabase.db";
    private static volatile TodoDatabase instance;

    static synchronized TodoDatabase getInstance(Context context){
        if (instance==null){
            instance = create(context);
        }
        return instance;
    }

    private static TodoDatabase create(final Context context){
        return Room.databaseBuilder(
                context,
                TodoDatabase.class,
                DB_NAME).build();
    }

    public abstract TodoDao getTodoDao();
}
