package com.dhananjay.lab5;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by dhananjay on 6/2/18.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView item1, item2;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        item1 = itemView.findViewById(R.id.item1);
        item2 = itemView.findViewById(R.id.item2);
    }
}
