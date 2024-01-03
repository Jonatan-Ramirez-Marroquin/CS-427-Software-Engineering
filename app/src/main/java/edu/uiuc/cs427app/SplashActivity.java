package edu.uiuc.cs427app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

/**
 * SplashActivity: This activity is displayed briefly upon the app's launch.
 * It checks if the user is already logged in and navigates to the appropriate screen.
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * Initializes the splash screen and checks the user's login status.
     * @param savedInstanceState saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkLoginStatus();
    }

    /**
     * Checks the user's login status.
     * If the user is already logged in, they're directed to the main activity.
     * Otherwise, they're directed to the login screen.
     */
    void checkLoginStatus() {
        Intent intent;

        String username = setupSharedPreferences();
        Log.i("user", username);

        if (!username.isEmpty()) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        finish();
    }

    /**
     * Retrieves the logged-in user's username from shared preferences.
     * @return the username if exists, or an empty string.
     */
    public String setupSharedPreferences() {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        return sharedPreferences.getString("username", "");
    }
}