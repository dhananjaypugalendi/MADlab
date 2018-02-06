package com.dhananjay.lab5;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by dhananjay on 6/2/18.
 */

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewHolder>{

    List<Item> itemList;

    RecyclerViewAdapter(List<Item> itemList){
        this.itemList = itemList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.item1.setText(itemList.get(position).item1);
        holder.item2.setText(itemList.get(position).item2);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
