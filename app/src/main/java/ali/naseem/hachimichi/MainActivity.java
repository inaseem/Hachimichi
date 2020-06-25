package ali.naseem.hachimichi;

import android.content.Intent;
import android.content.pm.PackageManager;
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
        int result = ActivityCompat.checkSelfPermission(this, "com.google.android.gms.permission.ACTIVITY_RECOGNITION");
        if (result != PackageManager.PERMISSION_GRANTED)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                "com.google.android.gms.permission.ACTIVITY_RECOGNITION",
                                "android.permission.ACTIVITY_RECOGNITION"}, 102);
            }
    }

}