package ph.remerico.firebasesample;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements InstanceIDService.TokenListener {

    TextView tokenValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tokenValue = findViewById(R.id.token_value);

        loadToken();

        InstanceIDService.addListener(this);
    }

    @Override
    protected void onDestroy() {
        InstanceIDService.removeListener(this);
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

}
