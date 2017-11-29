package com.example.sethu.a12_control_media_play_cs580;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.widget.Toast;
import android.media.AudioManager;
import android.content.Intent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(sensor == null) {
            System.out.println("NO SENSORRRRR");
            finish(); // Close app
        }else{
            System.out.println("GOTTTTTTTTT THEEEEE SENSORRRRR");
        }

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

 @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // More code goes here
        if(sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (sensorEvent.values[0] < sensor.getMaximumRange()) {
                // Detected something nearby
    //            getWindow().getDecorView().setBackgroundColor(Color.RED);
                Toast.makeText(getApplicationContext(), "NEAR", Toast.LENGTH_SHORT).show();
                AudioManager mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

                if (mAudioManager.isMusicActive()) {
                    System.out.println("MUSIC PLAYING=========="+"\n");
                    Intent i = new Intent("com.android.music.musicservicecommand");

                    i.putExtra("command", "pause");
                    MainActivity.this.sendBroadcast(i);
                }

            } else {
                // Nothing is nearby
    //            getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                Toast.makeText(getApplicationContext(), "FAR", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}
