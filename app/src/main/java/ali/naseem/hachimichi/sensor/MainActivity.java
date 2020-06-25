package ali.naseem.hachimichi.sensor;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationServices;

import java.util.HashMap;

import ali.naseem.hachimichi.R;

public class MainActivity extends AppCompatActivity
        implements ConnectionCallbacks, OnConnectionFailedListener {

    public static String TAG = MainActivity.class.getSimpleName();

    // Sensor member variables
    private RecyclerView.Adapter sensorRecyclerViewAdapter;
    private final HashMap<Integer, SensorInfo> sensorInfoMap = new HashMap<>();
    private SensorDataReceiver sensorDataReceiver;

    // Activity recognition member variables
    private static final String KEY_ACTIVITY_RECOGNITION_UPDATE = "key_activity_recognition_update";
    private BroadcastReceiver activityRecognitionReceiver;
    private PendingIntent activityRecognitionIntent;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_sensor);

        buildSensorRecyclerView();
        buildGoogleApiClient();
        buildSensors();
    }

    /**
     * Called after onCreate(Bundle) — or after onRestart() when the activity had been stopped, but
     * is now again being displayed to the user. It will be followed by onResume().
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");

        googleApiClient.connect();
    }

    /**
     * Called after onRestoreInstanceState(Bundle), onRestart(), or onPause(), for your activity to
     * start interacting with the user. This is a good place to begin animations, open
     * exclusive-access devices (such as the camera), etc.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    /**
     * Called when you are no longer visible to the user. You will next receive either onRestart(),
     * onDestroy(), or nothing, depending on later user activity.
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");

        stopSensors();
    }

    /**
     * Perform any final cleanup before an activity is destroyed. This can happen either because the
     * activity is finishing (someone called finish() on it, or because the system is temporarily
     * destroying this instance of the activity to save space. You can distinguish between these two
     * scenarios with the isFinishing() method.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

        stopSensors();
    }

    /**
     * Build the recycler view for all sensor.
     */
    private void buildSensorRecyclerView() {
        Log.d(TAG, "buildSensorRecyclerView");

        // Get recycler view
        final RecyclerView sensorRecyclerView = (RecyclerView) findViewById(R.id.sensor_recycler_view);
        sensorRecyclerView.setHasFixedSize(true);
        sensorRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        sensorInfoMap.put(SensorInfo.ACTIVITY_RECOGNITION, new SensorInfo(R.string.activity_recognition, R.mipmap.ic_launcher));
        sensorInfoMap.put(SensorInfo.ACCELERATION, new SensorInfo(R.string.acceleration, R.mipmap.ic_launcher));
        // TODO: Implement more sensors

        // Set recycler view adapter
        sensorRecyclerViewAdapter = new SensorRecyclerViewAdapter(getApplicationContext(), sensorRecyclerView, sensorInfoMap);
        sensorRecyclerView.setAdapter(sensorRecyclerViewAdapter);
    }

    /**
     * Build the Google API client.
     */
    private void buildGoogleApiClient() {
        Log.d(TAG, "buildGoogleApiClient");

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(ActivityRecognition.API)
                .build();
    }

    /**
     * Build all sensors.
     */
    private void buildSensors() {
        activityRecognitionReceiver = new ActivityRecognitionReceiver(sensorInfoMap.get(SensorInfo.ACTIVITY_RECOGNITION), sensorRecyclerViewAdapter);

        final SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorDataReceiver = new SensorDataReceiver(sensorInfoMap, sensorRecyclerViewAdapter, sensorManager);
    }

    /**
     * Start all sensors.
     */
    private void startSensors() {
        startActivityRecognitionUpdates();
        sensorDataReceiver.startSensors();
    }

    /**
     * Stop all sensors.
     */
    private void stopSensors() {
        stopActivityRecognitionUpdates();
        sensorDataReceiver.stopSensors();
        googleApiClient.disconnect();
    }

    /**
     * Method of {@link ConnectionCallbacks} interface.
     * After calling connect(), this method will be invoked asynchronously when the connect request
     * has successfully completed.
     *
     * @param connectionHint Bundle of data provided to clients by Google Play services. May be null
     *                       if no content is provided by the service.
     */
    @Override
    public void onConnected(final Bundle connectionHint) {
        Log.d(TAG, "onConnected");

        startSensors();
    }

    /**
     * Method of {@link ConnectionCallbacks} interface.
     * Called when the client is temporarily in a disconnected state.
     *
     * @param cause The reason for the disconnection. Defined by constants CAUSE_*.
     */
    @Override
    public void onConnectionSuspended(final int cause) {
        Log.d(TAG, "onConnectionSuspended");

        switch (cause) {
            case CAUSE_NETWORK_LOST:
                Log.w(TAG, "onConnectionSuspended: " + "Cause: Network lost (Cause ID: " + cause + ")");
                break;
            case CAUSE_SERVICE_DISCONNECTED:
                Log.w(TAG, "onConnectionSuspended: " + "Cause: Service disconnected (Cause ID: " + cause + ")");
                break;
            default:
                Log.w(TAG, "onConnectionSuspended: " + "Cause: unknown (Cause ID: " + cause + ")");
                break;
        }
    }

    /**
     * Method of {@link OnConnectionFailedListener} interface.
     *
     * @param connectionResult The result of the Google API client connection.
     */
    @Override
    public void onConnectionFailed(final ConnectionResult connectionResult) {
        // TODO: Implement behaviour for a failed  connection
    }

    /**
     * Start activity recognition updates.
     */
    private void startActivityRecognitionUpdates() {
        Log.d(TAG, "startActivityRecognitionUpdates");

        this.registerReceiver(activityRecognitionReceiver, new IntentFilter(KEY_ACTIVITY_RECOGNITION_UPDATE));
        final Intent intent = new Intent(KEY_ACTIVITY_RECOGNITION_UPDATE);
        activityRecognitionIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(googleApiClient, 0, activityRecognitionIntent);
    }

    /**
     * Stop activity recognition updates.
     */
    private void stopActivityRecognitionUpdates() {
        if (googleApiClient.isConnected()) {
            ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(googleApiClient, activityRecognitionIntent);
            this.unregisterReceiver(activityRecognitionReceiver);
        }
    }
}
