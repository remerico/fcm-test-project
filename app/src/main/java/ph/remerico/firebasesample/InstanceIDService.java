package ph.remerico.firebasesample;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rem on 24/05/2018.
 */

public class InstanceIDService extends FirebaseInstanceIdService {

    static final String TAG = "FirebaseSample";

    static List<TokenListener> tokenListeners = new ArrayList<>();

    @Override
    public void onTokenRefresh() {

        // Get updated InstanceID token.
        final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

        //sendRegistrationToServer(refreshedToken);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                for (TokenListener tl : tokenListeners) {
                    tl.onTokenRefresh(refreshedToken);
                }
            }
        });

    }

    public static void addListener(TokenListener listener) {
        tokenListeners.add(listener);
    }

    public static void removeListener(TokenListener listener) {
        tokenListeners.remove(listener);
    }


    public interface TokenListener {
        void onTokenRefresh(String token);
    }

}
