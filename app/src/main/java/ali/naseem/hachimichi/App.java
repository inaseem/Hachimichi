package ali.naseem.hachimichi;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(getApplicationContext());
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword("naseem@ali.com", "naseemali").addOnSuccessListener(authResult -> {
            Log.d("Auth", "Authenticated Succeeded");
        }).addOnFailureListener(authResult -> {
            Log.d("Auth", "Authenticated Failed");
        });
    }
}
