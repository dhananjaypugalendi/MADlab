package com.dhananjay.lab5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Item> itemList;
    String urlString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        urlString = "http://10.1.74.230:5000/db";
        HttpTask httpTask = new HttpTask();
        itemList = new ArrayList<>();
        try {
            itemList = httpTask.execute(urlString).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        /*for(int i=0;i<20;i++){
            itemList.add(new Item(""+i,""+i));
        }*/
        recyclerView.setAdapter(new RecyclerViewAdapter(itemList));
    }
}
