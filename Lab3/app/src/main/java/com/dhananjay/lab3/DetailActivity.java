package com.dhananjay.lab3;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        if (savedInstanceState == null) {
            DetailFragment detail = new DetailFragment();
            detail.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.frag_container, detail).commit();
        }
    }
}
