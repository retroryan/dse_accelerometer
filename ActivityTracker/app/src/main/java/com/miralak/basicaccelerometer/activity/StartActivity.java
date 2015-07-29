package com.miralak.basicaccelerometer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.miralak.basicaccelerometer.R;


/**
 * The idea and original application comes from Amira LAKHAL:
 * http://www.duchess-france.org/accelerometer-time-series-and-prediction-with-android-cassandra-and-spark/
 */
public class StartActivity extends ActionBarActivity {

    public static final String URL = "restURL";
    private EditText restURL;
    public static final String USER_ID = "userID";
    private EditText userID;
    public static final String NOTES_ID = "notesID";
    private EditText notesID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        restURL = (EditText) findViewById(R.id.editURL);
        userID =  (EditText) findViewById(R.id.userID);
        notesID =  (EditText) findViewById(R.id.notesID);

        Button myButton = (Button) findViewById(R.id.button_start);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(StartActivity.this, AccelerometerActivity.class);
                intent.putExtra(URL, restURL.getText().toString());
                intent.putExtra(USER_ID, userID.getText().toString());
                intent.putExtra(NOTES_ID, notesID.getText().toString());
                startActivity(intent);
            }
        });
    }
}
