package com.example.sethu.a12_control_media_play_cs580;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.widget.Toast;
import android.media.AudioManager;
import android.content.Intent;


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
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

 @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // More code goes here
        if(sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (sensorEvent.values[0] < sensor.getMaximumRange()) {
                try{
                // Detected something nearby
                    Toast.makeText(getApplicationContext(), "NEAR", Toast.LENGTH_SHORT).show();
                    AudioManager mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
                    System.out.println("SENSOR IN CLOSE RANGE==========" + "\n");
                    Intent i = new Intent("com.android.music.musicservicecommand");

                    i.putExtra("command", "togglepause");
                    MainActivity.this.sendBroadcast(i);

                }catch(Exception e){
                    e.printStackTrace();
                }

            } else {
                // Nothing is nearby
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
