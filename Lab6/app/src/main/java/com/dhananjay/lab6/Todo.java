package com.dhananjay.lab6;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Created by dhananjay on 20/2/18.
 */

@Entity(tableName = "todo")
public class Todo {

    @PrimaryKey
    @ColumnInfo(name = "todo_id")
    @NonNull
    String todoId;

    @ColumnInfo(name = "todo_task")
    String todoTask;

    @ColumnInfo(name = "is_done")
    boolean isDone;

    @ColumnInfo(name = "created_time")
    long createdTime;

    public Todo(String todoTask, boolean isDone){
        this.todoTask = todoTask;
        this.isDone = isDone;
        todoId = UUID.randomUUID().toString();
        createdTime = System.currentTimeMillis();
    }

    @Ignore
    public void toggleComplete(){
        isDone = !isDone;
    }

}
