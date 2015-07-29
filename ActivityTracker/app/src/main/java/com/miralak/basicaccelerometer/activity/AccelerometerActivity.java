package com.miralak.basicaccelerometer.activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.miralak.basicaccelerometer.R;
import com.miralak.basicaccelerometer.api.RestAPI;
import com.miralak.basicaccelerometer.model.Acceleration;

import java.util.Date;

import retrofit.RestAdapter;

public class AccelerometerActivity extends ActionBarActivity implements SensorEventListener{

    private String restURL;
    private String userID;
    private String notesID;

    private TextView acceleration;
    private RestAPI restAPI;

    private SensorManager sm;
    private Sensor accelerometer;

    private LocationHelper locationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        startSensor();

        //locationHelper = new LocationHelper();
        //locationHelper.onCreate(this);

        acceleration = (TextView) findViewById(R.id.acceleration);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            restURL = extras.getString(StartActivity.URL);
            userID = extras.getString(StartActivity.USER_ID);
            notesID = extras.getString(StartActivity.NOTES_ID);
        }

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(restURL)
                .build();

        System.out.println("restURL = " + restURL);
        restAPI = restAdapter.create(RestAPI.class);

        Button myButton = (Button) findViewById(R.id.button_exit);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSensor();
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startSensor();
    }

    private void startSensor() {
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stopSensor() {
        sm.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_accelerometer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Acceleration capturedAcceleration = getAccelerationFromSensor(event);
        updateTextView(capturedAcceleration);
        new SendAccelerationAsyncTask().execute(capturedAcceleration);
    }

    private void updateTextView(Acceleration capturedAcceleration) {
        acceleration.setText("X:" + capturedAcceleration.getX() +
                "\nY:" + capturedAcceleration.getY() +
                "\nZ:" + capturedAcceleration.getZ() +
                "\nTimestamp:" + capturedAcceleration.getTimestamp());
    }

    private Acceleration getAccelerationFromSensor(SensorEvent event) {
        long timestamp = (new Date()).getTime() + (event.timestamp - System.nanoTime()) / 1000000L;
        return new Acceleration(event.values[0], event.values[1], event.values[2], timestamp, userID, notesID);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Do nothing
    }

    /**
     * Asyncronous task to post request to a Rest API.
     */
    private class SendAccelerationAsyncTask extends AsyncTask<Acceleration, Void, Void>{

        @Override
        protected Void doInBackground(Acceleration... params) {
            try {
                //System.out.println("sending to restURL = " + restURL);
                //System.out.println("restAPI = " + restAPI.toString());
                restAPI.sendAccelerationValues(params[0]);
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
