package ph.remerico.firebasesample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rem on 24/05/2018.
 */

public class IncomingPushService extends FirebaseMessagingService {

    public interface PushListener {
        void pushPayloadReceived(Map<String, String> data);
    }

    static final String TAG = "FirebaseSample";

    static List<PushListener> pushListeners = new ArrayList<>();

    public static void addListener(PushListener listener) {
        pushListeners.add(listener);
    }

    public static void removeListener(PushListener listener) {
        pushListeners.remove(listener);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            final Map<String, String> data = remoteMessage.getData();

            for (String key : data.keySet()) {
                Log.d(TAG, key + ":" + data.get(key));
            }

            // Publish event
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    for (PushListener listener : pushListeners) {
                        listener.pushPayloadReceived(data);
                    }
                }
            });

            // Show notification
            showPush(data);

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //scheduleJob();
            } else {
                // Handle message within 10 seconds
                //handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            Log.d(TAG, "Message Notification Title: " + notification.getTitle());
            Log.d(TAG, "Message Notification Body: " + notification.getBody());
        }

    }


    protected void showPush(Map<String, String> data) {

        NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "test",
                    "test",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), "test")
                .setContentTitle(data.get("plush.title"))
                .setContentText(data.get("plush.content"))
                .setSmallIcon(R.drawable.ic_plush_small)
                .build();

        notificationManager.notify(1000, notification);

    }

}
