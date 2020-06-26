package ali.naseem.hachimichi.sensor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import ali.naseem.hachimichi.R;

@SuppressLint({"SetTextI18n", "DefaultLocale"})
public class SensorActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private int sensorType;
    private SensorEventListener listener;
    private TextView display;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback callback;
    private ActivityRecognitionClient activityRecognitionClient;
    private PendingIntent pendingIntent;
    public static final String ACTION = "ali.naseem.hachimichi.INTENT_ACTION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        display = findViewById(R.id.display);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        activityRecognitionClient = ActivityRecognition.getClient(this);
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
                    StringBuilder sb = new StringBuilder();
                    sb.append("Accuracy: ").append(location.getAccuracy()).append("\n");
                    sb.append("Latitude: ").append(location.getLatitude()).append("\n");
                    sb.append("Longitude: ").append(location.getLongitude()).append("\n");
                    sb.append("Altitude: ").append(location.getAltitude()).append("\n");
                    show("Location (GPS+NETWORK)", sb.toString());
                }
            }
        };

        findViewById(R.id.accelerator).setOnClickListener(v -> setSensorListener(Sensor.TYPE_ACCELEROMETER, PackageManager.FEATURE_SENSOR_ACCELEROMETER, "Accelerometer"));
        findViewById(R.id.magnetometer).setOnClickListener(v -> setSensorListener(Sensor.TYPE_MAGNETIC_FIELD, PackageManager.FEATURE_SENSOR_COMPASS, "Magnetometer"));
        findViewById(R.id.pressure).setOnClickListener(v -> setSensorListener(Sensor.TYPE_PRESSURE, PackageManager.FEATURE_SENSOR_BAROMETER, "Pressure Sensor"));
        findViewById(R.id.light).setOnClickListener(v -> setSensorListener(Sensor.TYPE_LIGHT, PackageManager.FEATURE_SENSOR_LIGHT, "Light Sensor"));
        findViewById(R.id.proximity).setOnClickListener(v -> setSensorListener(Sensor.TYPE_PROXIMITY, PackageManager.FEATURE_SENSOR_PROXIMITY, "Proximity Sensor"));
        findViewById(R.id.gps).setOnClickListener(v -> setLocationListener());
        findViewById(R.id.activity).setOnClickListener(v -> setActivityListener());
        findViewById(R.id.stepCounter).setOnClickListener(v -> setSensorListener(Sensor.TYPE_STEP_COUNTER, PackageManager.FEATURE_SENSOR_STEP_COUNTER, "Step Counter Sensor"));
        findViewById(R.id.stepDetector).setOnClickListener(v -> setSensorListener(Sensor.TYPE_STEP_DETECTOR, PackageManager.FEATURE_SENSOR_STEP_DETECTOR, "Step Detector Sensor"));
        findViewById(R.id.temperature).setOnClickListener(v -> setSensorListener(Sensor.TYPE_AMBIENT_TEMPERATURE, PackageManager.FEATURE_SENSOR_AMBIENT_TEMPERATURE, "Ambient Temperature"));
        findViewById(R.id.humidity).setOnClickListener(v -> setSensorListener(Sensor.TYPE_RELATIVE_HUMIDITY, PackageManager.FEATURE_SENSOR_RELATIVE_HUMIDITY, "Relative Humidity"));
        findViewById(R.id.gravity).setOnClickListener(v -> setSensorListener(Sensor.TYPE_GRAVITY, null, "Gravity Sensor"));
        findViewById(R.id.rotation).setOnClickListener(v -> setSensorListener(Sensor.TYPE_ROTATION_VECTOR, null, "Rotation Vector"));
        findViewById(R.id.heart_rate).setOnClickListener(v -> setSensorListener(Sensor.TYPE_HEART_RATE, PackageManager.FEATURE_SENSOR_HEART_RATE, "Heart Rate"));
        findViewById(R.id.gyroscope).setOnClickListener(v -> setSensorListener(Sensor.TYPE_GYROSCOPE, PackageManager.FEATURE_SENSOR_GYROSCOPE, "Gyroscope"));
        findViewById(R.id.linear_accelerator).setOnClickListener(v -> setSensorListener(Sensor.TYPE_LINEAR_ACCELERATION, null, "Linear Acceleration"));
        findViewById(R.id.motion).setOnClickListener(v -> setSensorListener(Sensor.TYPE_SIGNIFICANT_MOTION, null, null));
    }

    private void show(String displayName, String data) {
        if (displayName != null)
            display.setText(displayName + "\n\n" + data);
        else display.setText(data);
    }

    private boolean available(String feature) {
        if (feature == null && mSensorManager.getDefaultSensor(sensorType) != null) return true;
        if (getPackageManager().hasSystemFeature(feature)) return true;
        Toast.makeText(this, "Sensor Not Available", Toast.LENGTH_SHORT).show();
        display.setText(null);
        return false;
    }

    private void setActivityListener() {
        unregisterListener();
        Intent intent = new Intent(this, ActivityRecognitionReceiver.class);
        intent.setAction(ActivityRecognitionReceiver.ACTION);

        pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        activityRecognitionClient.requestActivityUpdates(3000, pendingIntent);
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onNewActivity(OnReceiveActivity activity) {
        show("Activity Recognition", activity.getName());
    }

    private void setLocationListener() {
        unregisterListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
            return;
        }

        fusedLocationClient.requestLocationUpdates(mLocationRequest, callback, Looper.myLooper());
    }

    private void setSensorListener(int sensorType, String featureName, String displayName) {
        unregisterListener();
        this.sensorType = sensorType;
        if (!available(featureName)) return;
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == sensorType) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Timestamp: ").append(event.timestamp).append("\n");
                    sb.append("Accuracy: ").append(event.accuracy).append("\n");
                    for (int i = 0; i < event.values.length; ++i) {
                        sb.append("Value ").append(i + 1).append(": ").append(event.values[i]).append("\n");
                    }
                    show(event.sensor.getName(), sb.toString());
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
        if (pendingIntent != null)
            activityRecognitionClient.removeActivityUpdates(pendingIntent).addOnCompleteListener(task -> {
                pendingIntent.cancel();
                EventBus.getDefault().unregister(this);
            });
        if (listener != null)
            mSensorManager.unregisterListener(listener);
    }

    private void registerListener() {
        if (display != null) display.setText(null);
        if (listener != null)
            mSensorManager.registerListener(listener, mSensorManager.getDefaultSensor(sensorType), SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterListener();
    }
}