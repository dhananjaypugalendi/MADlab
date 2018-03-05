package com.dhananjay.lab6;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnLongClickListener, AdapterView.OnItemLongClickListener {

    TodoDao todoDao;
    List<Todo> todoList;
    TodoAdapter adapter;
    ListView listView;
    Button button;
    EditText editText;
    SqliteDatabaseAdapter sqliteDatabaseAdapter;
    String TAG = "main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.addTodoButton);
        editText = findViewById(R.id.addTodo);
        listView = findViewById(R.id.todoList);

        //sqliteDatabaseAdapter = new SqliteDatabaseAdapter(this);
        todoDao = TodoDatabase
                .getInstance(getApplicationContext()).getTodoDao();

        todoList = new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todo = editText.getText().toString();
                if (!todo.equals("")){
                    insertTodo(new Todo(todo,false));
                }
            }
        });
        //todoList.add(new Todo("hello", false));
        //todoList.add(new Todo("bye",false));
        adapter = new TodoAdapter(this, R.layout.todo_item, todoList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        getAllTodos();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick: "+position);
        updateTodo(todoList.get(position));
    }


    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemLongClick: "+position);
        deleteTodo(todoList.get(position));
        return false;
    }

    public void getAllTodos(){
        new AsyncTask<Void, Void, List<Todo>>() {
            @Override
            protected List<Todo> doInBackground(Void... voids) {
                todoList.clear();
                todoList.addAll(todoDao.getAllTodos());
                return todoList;
            }

            @Override
            protected void onPostExecute(List<Todo> todos) {
                adapter.notifyDataSetChanged();
                Log.d(TAG, "onPostExecute: "+todoList.size());
                super.onPostExecute(todos);
            }
        }.execute();
        /*todoList.clear();
        todoList.addAll(sqliteDatabaseAdapter.getAllData());
        adapter.notifyDataSetChanged();*/

    }

    public void insertTodo(final Todo todo){
        new AsyncTask<Todo, Void, List<Todo>>() {
            @Override
            protected List<Todo> doInBackground(Todo... todos) {
                todoDao.insertTodo(todo);
                todoList.clear();
                todoList.addAll(todoDao.getAllTodos());
                return todoList;
            }

            @Override
            protected void onPostExecute(List<Todo> todos) {
                adapter.notifyDataSetChanged();
                Log.d(TAG, "onPostExecute: "+todoList.size());
                super.onPostExecute(todos);
            }
        }.execute();
        Toast.makeText(this,"inserted",Toast.LENGTH_SHORT);
        /*sqliteDatabaseAdapter.insertData(todo);
        todoList.clear();
        todoList.addAll(sqliteDatabaseAdapter.getAllData());
        adapter.notifyDataSetChanged();*/

    }

    public void deleteTodo(final Todo todo){
        new AsyncTask<Todo, Void, List<Todo>>() {
            @Override
            protected List<Todo> doInBackground(Todo... todos) {
                todoDao.delete(todo);
                todoList.clear();
                todoList.addAll(todoDao.getAllTodos());
                return todoList;
            }

            @Override
            protected void onPostExecute(List<Todo> todos) {
                adapter.notifyDataSetChanged();
                Log.d(TAG, "onPostExecute: "+todoList.size());
                super.onPostExecute(todos);
            }
        }.execute();
        /*Toast.makeText(this,"deleted",Toast.LENGTH_SHORT);
        sqliteDatabaseAdapter.deleteData(todo);
        todoList.clear();
        todoList.addAll(sqliteDatabaseAdapter.getAllData());
        adapter.notifyDataSetChanged();*/
    }

    public void updateTodo(final Todo todo){
        new AsyncTask<Todo, Void, List<Todo>>() {
            @Override
            protected List<Todo> doInBackground(Todo... todos) {
                todo.toggleComplete();
                todoDao.update(todo);
                todoList.clear();
                todoList.addAll(todoDao.getAllTodos());
                return todoList;
            }

            @Override
            protected void onPostExecute(List<Todo> todos) {
                adapter.notifyDataSetChanged();
                Log.d(TAG, "onPostExecute: "+todoList.size());
                super.onPostExecute(todos);
            }
        }.execute();
        /*Toast.makeText(this,"deleted",Toast.LENGTH_SHORT);
        sqliteDatabaseAdapter.updateData(todo);
        todoList.clear();
        todoList.addAll(sqliteDatabaseAdapter.getAllData());
        adapter.notifyDataSetChanged();*/
    }

}
