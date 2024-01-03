package edu.uiuc.cs427app;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


/**
 * ThemeActivity: An abstract activity that serves as a base for other activities.
 * It checks the user's login status and applies a theme based on user's preference.
 */
public class ThemeActivity extends AppCompatActivity{
    /**
     * Initializes the activity.
     * If the user isn't logged in, redirects them to the LoginActivity.
     * Otherwise, applies the theme specified in the user's preferences.
     *
     * @param savedInstanceState saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isUserLoggedIn()) {
            redirectToLogin();
            return;
        }

        applyUserTheme();
    }

    /**
     * Checks if the user is logged in.
     *
     * @return true if the user is logged in; otherwise, false.
     */
    private boolean isUserLoggedIn() {
        SharedPreferences preferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        return preferences.getBoolean("isLoggedIn", false);
    }

    /**
     * Redirects the user to the LoginActivity and finishes the current activity.
     */
    private void redirectToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Applies a theme based on the user's preferences.
     */
    private void applyUserTheme() {
        SharedPreferences preferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String theme = preferences.getString("theme", "Theme.MyFirstApp.Purple");

        switch (theme) {
            case "Theme.MyFirstApp.Pink":
                setTheme(R.style.Theme_MyFirstApp_Pink);
                break;
            case "Theme.MyFirstApp.Red":
                setTheme(R.style.Theme_MyFirstApp_Red);
                break;
            default:
                setTheme(R.style.Theme_MyFirstApp_Purple);
                break;
        }
    }

}

