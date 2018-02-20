package com.dhananjay.lab6;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by dhananjay on 20/2/18.
 */

public class TodoAdapter extends ArrayAdapter<Todo> {
    public TodoAdapter(@NonNull Context context, int resource, @NonNull List<Todo> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Todo todo = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }
        RadioButton todoCompleted = convertView.findViewById(R.id.todoCompleted);
        TextView todoTask =  convertView.findViewById(R.id.todoTask);
        if(todo.isDone)
            todoCompleted.setChecked(true);
        else
            todoCompleted.setChecked(false);
        TextView todoCreated =  convertView.findViewById(R.id.todoCreated);
        todoTask.setText(todo.todoTask);
        Date date=new Date(todo.createdTime);
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMM yy HH:mm:ss");
        String dateText = df2.format(date);
        todoCreated.setText(dateText);
        return convertView;
    }
}
