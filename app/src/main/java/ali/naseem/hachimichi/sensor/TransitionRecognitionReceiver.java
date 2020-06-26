package ali.naseem.hachimichi.sensor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

public class TransitionRecognitionReceiver extends BroadcastReceiver {

    private Context mContext;
    public static final String ACTION = "ali.naseem.hachimichi.INTENT_ACTION";

    public void onReceive(Context context, Intent intent) {
        mContext = context;
        if (intent != null && ACTION.equals(intent.getAction())) {
            if (ActivityRecognitionResult.hasResult(intent)) {
                ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);

                Log.d("Data", "onReceive");
                if (result != null) {
                    processTransitionResult(result);
                    Log.d("Data", "Has Result");
                }
            }
        }
    }


    private void processTransitionResult(ActivityRecognitionResult result) {
        for (DetectedActivity activity : result.getProbableActivities()) {
            saveTransition(activity);
        }
    }

    private void saveTransition(DetectedActivity activity) {
        // Save in Preferences
        SharedPreferences sharedPref = mContext.getSharedPreferences(
                "hachimichi", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String transitions = TransitionRecognitionUtils.createTransitionString(activity);
        editor.putString("transition", transitions);
        Log.d("Data", transitions);
        editor.commit();
    }
}
