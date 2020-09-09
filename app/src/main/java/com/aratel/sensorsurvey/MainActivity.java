package com.aratel.sensorsurvey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager; // the sensor manager is a system that lets you access the device sensors

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> sensorList = mSensorManager != null ? mSensorManager.getSensorList(Sensor.TYPE_ALL) : null; // Sensor.TYPE_ALL indicates all the available sensors

        StringBuilder sensorText = new StringBuilder();

        if (sensorList != null) {
            for (Sensor currentSensor : sensorList) {
                sensorText.append(currentSensor.getName()).append(System.getProperty("line.separator"));
            }
        }

        TextView sensorTextView = findViewById(R.id.sensor_list);
        sensorTextView.setText(sensorText);

        sensorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,GetValueFromSensorActivity.class));
            }
        });
    }
}
