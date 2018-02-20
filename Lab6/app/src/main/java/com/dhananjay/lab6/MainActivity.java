package com.dhananjay.lab6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnLongClickListener {

    TodoDao todoDao;
    List<Todo> todoList;
    TodoAdapter adapter;
    ListView listView;
    Button button;
    EditText editText;

    String TAG = "main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.addTodoButton);
        editText = findViewById(R.id.addTodo);
        listView = findViewById(R.id.todoList);

        todoDao = TodoDatabase
                .getInstance(getApplicationContext()).getTodoDao();

        todoList = new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todo = editText.getText().toString();
                if (!todo.equals("")){
                    todoDao.insertTodo(new Todo(todo,false));
                }
            }
        });
        //todoList.add(new Todo("hello", false));
        //todoList.add(new Todo("bye",false));
        adapter = new TodoAdapter(this, R.layout.todo_item, todoList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnLongClickListener(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                todoList = todoDao.getAllTodos();
                Log.d(TAG, "run: "+todoList.size());
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // you can update fragment UI at here
                        }
                    });
                }
            }
        }) .start();
        Log.d(TAG, "onCreate: "+todoList.size());
        for(int i =0; i<todoList.size();i++){
            Log.d(TAG, "onCreate: "+todoList.get(i).todoTask);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
