package ali.naseem.hachimichi.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ali.naseem.hachimichi.R;

public class SensorActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private int sensorType;
    private SensorEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor2);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        setProximityListener();
        setAccelerometerListener();
    }

    private void setProximityListener() {
        sensorType = Sensor.TYPE_PROXIMITY;
        unregisterListener();
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float maxRange = event.sensor.getMaximumRange();
                if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    if (event.values[0] < maxRange) {
                        //near
                        Toast.makeText(getApplicationContext(), "near", Toast.LENGTH_SHORT).show();
                    } else {
                        //far
                        Toast.makeText(getApplicationContext(), "far", Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(getApplicationContext(), maxRange + "    " + event.values[0], Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    private void setAccelerometerListener() {
        sensorType = Sensor.TYPE_ACCELEROMETER;
        unregisterListener();
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    //get the current values of the accelerometer for each axis
                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];
                    Toast.makeText(SensorActivity.this, x + " , " + y + " , " + z, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterListener();
    }

    private void unregisterListener() {
        if (listener != null)
            mSensorManager.unregisterListener(listener);
    }

    private void registerListener() {
        if (listener != null)
            mSensorManager.registerListener(listener, mSensorManager.getDefaultSensor(sensorType), SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterListener();
    }
}