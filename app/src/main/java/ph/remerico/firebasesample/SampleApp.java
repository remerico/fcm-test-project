package ph.remerico.firebasesample;

import android.app.Application;
import android.util.Log;

/**
 * Created by rem on 24/05/2018.
 */

public class SampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("FirebaseSample", "Application.onCreate()");
    }

}
