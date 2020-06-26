package ali.naseem.hachimichi.sensor;

import com.google.android.gms.location.DetectedActivity;

public class ActivityRecognitionUtils {

    public static String createTransitionString(DetectedActivity activity) {
        String theActivity = toActivityString(activity.getType());
        return ("Transition: " + theActivity);
    }


    public static String toActivityString(Integer activity) {
        switch (activity) {
            case DetectedActivity.STILL:
                return "STILL";
            case DetectedActivity.WALKING:
                return "WALKING";
            case DetectedActivity.ON_BICYCLE:
                return "ON_BICYCLE";
            case DetectedActivity.ON_FOOT:
                return "ON_FOOT";
            case DetectedActivity.IN_VEHICLE:
                return "IN_VEHICLE";
            case DetectedActivity.RUNNING:
                return "RUNNING";
            case DetectedActivity.TILTING:
                return "TILTING";
            default:
                return "UNKNOWN";
        }
    }

}
