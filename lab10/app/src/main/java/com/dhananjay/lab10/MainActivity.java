package com.dhananjay.lab10;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SmsReceiver.Listener {

    String TAG = "MainActivity";
    EditText phoneNo, smsText;
    TextView receivedSms;
    private SmsReceiver smsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phoneNo = findViewById(R.id.phone_no);
        smsText = findViewById(R.id.sms_text);
        receivedSms = findViewById(R.id.received_message);
        smsReceiver = new SmsReceiver();
        registerReceiver(smsReceiver, new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));
        smsReceiver.setListener(this);
    }

    @SuppressLint("MissingPermission")
    public void makeCall(View view) {
        if(!phoneNo.getText().toString().equals("")){
            Intent intent = new Intent(android.content.Intent.ACTION_CALL, Uri.parse("tel: +91"+phoneNo.getText().toString()));
            startActivity(intent);
        }
    }

    public void sendSms(View view) {
        if(!phoneNo.getText().toString().equals("") && !smsText.getText().toString().equals("")){
            SmsManager.getDefault().sendTextMessage(phoneNo.getText().toString(), null, smsText.getText().toString(), null, null);
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(smsReceiver);
        super.onDestroy();
    }

    @Override
    public void onTextReceived(String text) {
        Log.d(TAG, "onTextReceived: "+text);
        receivedSms.setText("Received SMS:\n"+text);
    }
}
