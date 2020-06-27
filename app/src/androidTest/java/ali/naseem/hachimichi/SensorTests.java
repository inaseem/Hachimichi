package ali.naseem.hachimichi;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class SensorTests {

    private SensorManager mSensorManager;
    private Context context;

    @Before
    public void init() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    @Test
    public void hasStepDetector() {
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        boolean hasStepDetector = context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_SENSOR_STEP_DETECTOR);
        if (hasStepDetector) {
            assertEquals(Sensor.TYPE_STEP_DETECTOR, sensor.getType());
        } else {
            assertNull(sensor);
        }
    }

    @Test
    public void hasStepCounter() {
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        boolean hasStepDetector = context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_SENSOR_STEP_COUNTER);
        if (hasStepDetector) {
            assertEquals(Sensor.TYPE_STEP_COUNTER, sensor.getType());
        } else {
            assertNull(sensor);
        }
    }

    @Test
    public void hasAccelerator() {
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        boolean hasStepDetector = context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_SENSOR_ACCELEROMETER);
        if (hasStepDetector) {
            assertEquals(Sensor.TYPE_ACCELEROMETER, sensor.getType());
        } else {
            assertNull(sensor);
        }
    }

    @Test
    public void hasAcceleratorLinear() {
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        boolean hasStepDetector = context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_SENSOR_ACCELEROMETER);
        if (hasStepDetector) {
            assertEquals(Sensor.TYPE_LINEAR_ACCELERATION, sensor.getType());
        } else {
            assertNull(sensor);
        }
    }

    @Test
    public void hasMagnetometer() {
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        boolean hasStepDetector = context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_SENSOR_COMPASS);
        if (hasStepDetector) {
            assertEquals(Sensor.TYPE_MAGNETIC_FIELD, sensor.getType());
        } else {
            assertNull(sensor);
        }
    }

    @Test
    public void hasPressureSensor() {
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        boolean hasStepDetector = context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_SENSOR_BAROMETER);
        if (hasStepDetector) {
            assertEquals(Sensor.TYPE_PRESSURE, sensor.getType());
        } else {
            assertNull(sensor);
        }
    }

    @Test
    public void hasProximitySensor() {
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        boolean hasStepDetector = context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_SENSOR_PROXIMITY);
        if (hasStepDetector) {
            assertEquals(Sensor.TYPE_PROXIMITY, sensor.getType());
        } else {
            assertNull(sensor);
        }
    }

    @Test
    public void hasGyroscope() {
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        boolean hasStepDetector = context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_SENSOR_GYROSCOPE);
        if (hasStepDetector) {
            assertEquals(Sensor.TYPE_GYROSCOPE, sensor.getType());
        } else {
            assertNull(sensor);
        }
    }

    @Test
    public void hasAmbientTemperatureSensor() {
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        boolean hasStepDetector = context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_SENSOR_AMBIENT_TEMPERATURE);
        if (hasStepDetector) {
            assertEquals(Sensor.TYPE_AMBIENT_TEMPERATURE, sensor.getType());
        } else {
            assertNull(sensor);
        }
    }

    @Test
    public void hasRelativeHumiditySensor() {
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        boolean hasStepDetector = context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_SENSOR_RELATIVE_HUMIDITY);
        if (hasStepDetector) {
            assertEquals(Sensor.TYPE_RELATIVE_HUMIDITY, sensor.getType());
        } else {
            assertNull(sensor);
        }
    }

    @Test
    public void hasGravitySensor() {
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        boolean hasStepDetector = context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_SENSOR_GYROSCOPE);
        if (hasStepDetector) {
            assertEquals(Sensor.TYPE_GRAVITY, sensor.getType());
        } else {
            assertNull(sensor);
        }
    }

    @Test
    public void hasHeartRateSensor() {
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        boolean hasStepDetector = context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_SENSOR_HEART_RATE);
        if (hasStepDetector) {
            assertEquals(Sensor.TYPE_HEART_RATE, sensor.getType());
        } else {
            assertNull(sensor);
        }
    }

    @Test
    public void hasLightSensor() {
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        boolean hasStepDetector = context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_SENSOR_LIGHT);
        if (hasStepDetector) {
            assertEquals(Sensor.TYPE_LIGHT, sensor.getType());
        } else {
            assertNull(sensor);
        }
    }
}
