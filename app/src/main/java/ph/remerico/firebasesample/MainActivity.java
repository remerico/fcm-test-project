package ph.remerico.firebasesample;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        InstanceIDService.TokenListener,
        IncomingPushService.PushListener {

    TextView tokenValue;
    TextView logs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tokenValue = findViewById(R.id.token_value);
        logs = findViewById(R.id.log_text);

        loadToken();

        IncomingPushService.addListener(this);
        InstanceIDService.addListener(this);
    }

    @Override
    protected void onDestroy() {
        InstanceIDService.removeListener(this);
        IncomingPushService.removeListener(this);
        super.onDestroy();
    }

    @Override
    public void onTokenRefresh(String token) {
        tokenValue.setText(token);
        saveToken(token);
    }


    protected void saveToken(String token) {
        SharedPreferences prefs = getSharedPreferences("prefs", 0);
        prefs.edit().putString("token", token).apply();
    }

    protected void loadToken() {
        SharedPreferences prefs = getSharedPreferences("prefs", 0);
        String token = prefs.getString("token", "");

        if (!TextUtils.isEmpty(token)) {
            tokenValue.setText(token);
        }
    }

    @Override
    public void pushPayloadReceived(Map<String, String> data) {
        logs.setText(data.toString());
    }

}
