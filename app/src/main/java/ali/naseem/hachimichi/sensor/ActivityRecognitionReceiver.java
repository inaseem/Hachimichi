package ali.naseem.hachimichi.sensor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import org.greenrobot.eventbus.EventBus;

public class ActivityRecognitionReceiver extends BroadcastReceiver {

    public static final String ACTION = "ali.naseem.hachimichi.INTENT_ACTION";

    public void onReceive(Context context, Intent intent) {
        if (intent != null && ACTION.equals(intent.getAction())) {
            if (ActivityRecognitionResult.hasResult(intent)) {
                ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);

                if (result != null) {
                    processTransitionResult(result);
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
        String transitions = ActivityRecognitionUtils.createTransitionString(activity);
        EventBus.getDefault().post(new OnReceiveActivity(transitions));
    }
}
