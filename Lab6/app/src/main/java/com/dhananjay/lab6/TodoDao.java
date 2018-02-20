package com.dhananjay.lab6;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by dhananjay on 20/2/18.
 */

@Dao
public interface TodoDao {

    @Query("SELECT * FROM todo")
    List<Todo> getAllTodos();

    @Insert
    void insertTodo(Todo... todos);

    @Update
    void update(Todo... todos);

    @Delete
    void delete(Todo... todos);
}
