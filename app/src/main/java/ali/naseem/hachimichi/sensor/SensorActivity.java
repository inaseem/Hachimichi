package ali.naseem.hachimichi.sensor;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import ali.naseem.hachimichi.R;

public class SensorActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private int sensorType;
    private SensorEventListener listener;
    private TextView display;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        display = findViewById(R.id.display);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(SensorManager.SENSOR_DELAY_NORMAL)
                .setFastestInterval(SensorManager.SENSOR_DELAY_FASTEST);
        callback = new LocationCallback() {
            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
            }

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    double lat = location.getLatitude();
                    double lang = location.getLongitude();
                    double alt = location.getAltitude();
                    String val = String.format("Lat: %f, Long: %f, Altitude: %f", lat, lang, alt);
                    show("Location", val, "");
                }
            }
        };
        findViewById(R.id.accelerator).setOnClickListener(v -> setAccelerometerListener());
        findViewById(R.id.magnetometer).setOnClickListener(v -> setMagnetometerListener());
        findViewById(R.id.pressure).setOnClickListener(v -> setPressureListener());
        findViewById(R.id.light).setOnClickListener(v -> setLightSensorListener());
        findViewById(R.id.proximity).setOnClickListener(v -> setProximityListener());
        findViewById(R.id.gps).setOnClickListener(v -> setLocationListener());
    }

    private void show(String type, float data, String unit) {
        display.setText(type + "\nValue: " + data + unit);
    }

    private void show(String type, String data, String unit) {
        display.setText(type + "\nValue: " + data + unit);
    }

    private void setLocationListener() {
        unregisterListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
            return;
        }

        fusedLocationClient.requestLocationUpdates(mLocationRequest, callback, Looper.myLooper());
//        fusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, location -> {
//                    if (location != null) {
//                        double lat = location.getLatitude();
//                        double lang = location.getLongitude();
//                        double alt = location.getAltitude();
//                        String val = String.format("Lat: %f, Long: %f, Altitude: %f", lat, lang, alt);
//                        show("Location", val, "");
//                    }
//                });
    }

    private void setProximityListener() {
        sensorType = Sensor.TYPE_PROXIMITY;
        unregisterListener();
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
//                    float maxRange = event.sensor.getMaximumRange();
//                    if (event.values[0] < maxRange) {
//                        //near
//                    } else {
//                        //far
//                    }
                    show("Proximity", event.values[0], "");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        registerListener();
    }

    private void setAccelerometerListener() {
        sensorType = Sensor.TYPE_ACCELEROMETER;
        unregisterListener();
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];
                    show("Accelerometer", "X = " + x + " ,Y =  " + y + " ,Z =  " + z, "");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        registerListener();
    }

    private void setMagnetometerListener() {
        sensorType = Sensor.TYPE_MAGNETIC_FIELD;
        unregisterListener();
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];
                    show("Magnetometer", "X = " + x + " ,Y =  " + y + " ,Z =  " + z, "");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        registerListener();
    }

    private void setPressureListener() {
        sensorType = Sensor.TYPE_PRESSURE;
        unregisterListener();
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
                    float val = event.values[0];
                    show("Pressure", val, "");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        registerListener();
    }

    private void setLightSensorListener() {
        sensorType = Sensor.TYPE_LIGHT;
        unregisterListener();
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                    float val = event.values[0];
                    show("Light", val, "");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        registerListener();
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
        if (callback != null && fusedLocationClient != null)
            fusedLocationClient.removeLocationUpdates(callback);
        if (listener != null)
            mSensorManager.unregisterListener(listener);
    }

    private void registerListener() {
        if (display != null) display.setText(null);
        if (listener != null)
            mSensorManager.registerListener(listener, mSensorManager.getDefaultSensor(sensorType), SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterListener();
    }
}