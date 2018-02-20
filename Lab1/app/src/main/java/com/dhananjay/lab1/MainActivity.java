package com.dhananjay.lab1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button send;
    private EditText input1, input2;
    private RadioButton add, sub;
    boolean isAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        add = findViewById(R.id.add);
        sub = findViewById(R.id.sub);
        send = findViewById(R.id.send);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent next = new Intent(this, ResultActivity.class);
        if(isAdd)
            next.putExtra("result", Integer.parseInt(input1.getText().toString()) + Integer.parseInt(input2.getText().toString()));
        else
            next.putExtra("result", Integer.parseInt(input1.getText().toString()) - Integer.parseInt(input2.getText().toString()));

        startActivity(next);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.add:
                if (checked)
                    isAdd = true;
                    if(sub.isChecked())
                        sub.setChecked(false);
                    break;
            case R.id.sub:
                if (checked)
                    isAdd = false;
                    if(add.isChecked())
                        add.setChecked(false);
                    break;
        }

    }
}
