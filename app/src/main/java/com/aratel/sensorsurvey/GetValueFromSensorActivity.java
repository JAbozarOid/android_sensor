package com.aratel.sensorsurvey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 * SensorEventListener means
 * When sensor data changes, the Android sensor framework generates an event (a SensorEvent) for that new data.
 * Your app can register listeners for these events, then handle the new sensor data in an onSensorChanged() callback. All of these tasks are part of the SensorEventListener interface.
 */
public class GetValueFromSensorActivity extends AppCompatActivity implements SensorEventListener {

    // Individual light and proximity sensors
    private Sensor mSensorProximity;
    private Sensor mSensorLight;

    // Textviews to display current sensor values
    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;

    private SensorManager mSensorManager; // the sensor manager is a system that lets you access the device sensors


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_value_from_sensor);

        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorProximity = mSensorManager != null ? mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) : null;
        mSensorLight = mSensorManager != null ? mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) : null;

        String sensor_error = getResources().getString(R.string.error_no_sensor);
        if (mSensorLight == null) {
            mTextSensorLight.setText(sensor_error);
        }

        if (mSensorProximity == null) {
            mTextSensorProximity.setText(sensor_error);
        }


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // sensorEvent object includes important properties of the event, such as which sensor is reporting new data and new data values.
        // both light and proximity sensors only report one value, in Values[0]
        // accelerometer reports data for the x-axis,y-axis and z-axis for every change in the values[0],values[1] and values[2]
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];

        switch (sensorType) {
            case Sensor.TYPE_LIGHT:
                // handle light sensor
                mTextSensorLight.setText(getResources().getString(R.string.label_light,currentValue));
                break;
            case Sensor.TYPE_PROXIMITY:
                // handle proximity sensor
                mTextSensorProximity.setText(getResources().getString(R.string.label_proximity,currentValue));
                break;
            default:
                // do nothing
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /**
     * Use the onStart() and onStop() methods to register and unregister your sensor listeners
     * As of Android 7.0 (API 24), apps can run in multi-window mode. Apps running in this mode are paused, but still visible on Screen.
     * Use onStart() and onStop() to ensure that sensors continue running even if the app is in multi-window mode
     */

    @Override
    protected void onStart() {
        super.onStart();

        // Each sensor that your app uses needs its own listener, and you should make sure that those sensors exist before you register a listener for them.
        // Use the registerListener() method from the SensorManager to register a listener.
        if (mSensorProximity != null) {
            // about the parameter third of registerListener -> The delay constant indicates how quickly new data is reported from the sensor
            // Make sure that your listener is registered with the minimum amount of new data it needs
            mSensorManager.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorLight != null) {
            mSensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    // unregister your sensor listeners when the app pauses.
    // onStop() method prevents the device from using power when the app is not visible
    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }
}


