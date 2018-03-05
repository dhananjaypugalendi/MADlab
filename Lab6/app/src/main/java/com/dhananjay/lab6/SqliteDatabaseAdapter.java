package com.dhananjay.lab6;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhananjay on 20/2/18.
 */


public class SqliteDatabaseAdapter {

    private DatabaseHelper databaseHelper;
    public SqliteDatabaseAdapter(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public long insertData (Todo todo) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(databaseHelper.NAME, todo.todoTask);
        contentValues.put(databaseHelper.CREATED_DATE, todo.createdTime);
        contentValues.put(databaseHelper.IS_DONE, todo.isDone);
        long id = db.insert(databaseHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public long updateData (Todo todo) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (todo.isDone)
            contentValues.put(databaseHelper.IS_DONE, 0);
        else
            contentValues.put(databaseHelper.IS_DONE, 1);
        long id = db.update(databaseHelper.TABLE_NAME, contentValues, databaseHelper.UID+"="+todo.todoId, null);
        return id;
    }

    public long deleteData(Todo todo) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long id = db.delete(databaseHelper.TABLE_NAME, databaseHelper.UID+"="+todo.todoId, null);
        return id;
    }

    public List<Todo> getAllData () {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String[] columns = {databaseHelper.UID, databaseHelper.NAME, databaseHelper.CREATED_DATE, databaseHelper.IS_DONE};
        Cursor cursor = db.query(databaseHelper.TABLE_NAME, columns, null, null, null, null, null);

        StringBuffer buffer = new StringBuffer();
        List<Todo> todos = new ArrayList<>();
        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            Todo t = new Todo(cursor.getString(1),(cursor.getInt(2) == 0) ? false : true);
            t.createdTime = cursor.getLong(2);
            todos.add(t);
        }

        return todos;
    }

    static class DatabaseHelper extends SQLiteOpenHelper{
        private static final String DATABASE_NAME = "lab";
        private static final String TABLE_NAME = "todo";
        private static final int DATABASE_VERSION = 1;
        private static final String UID = "_id";
        private static final String NAME = "task";
        private static final String CREATED_DATE = "created_date";
        private static final String IS_DONE = "is_done";
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255), "+CREATED_DATE+" LONG ,"+ IS_DONE + " INTEGER );";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(CREATE_TABLE);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                sqLiteDatabase.execSQL(DROP_TABLE);
                onCreate(sqLiteDatabase);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
