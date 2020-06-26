package ali.naseem.hachimichi;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import ali.naseem.hachimichi.fragments.SliderFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.sensorCheckButton).setOnClickListener(v -> openSensors());
        addImageFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestPermissions();
    }

    public void addImageFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frameLayout, SliderFragment.instance()).commit();
    }

    public void openSensors() {
        Intent intent = new Intent(this, ali.naseem.hachimichi.sensor.SensorActivity.class);
        startActivity(intent);
    }

    public void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            "com.google.android.gms.permission.ACTIVITY_RECOGNITION",
                            Manifest.permission.INTERNET,
                            Manifest.permission.ACTIVITY_RECOGNITION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, 102);
        }
    }

}