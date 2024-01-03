package edu.uiuc.cs427app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * LoginActivity: An activity allowing users to login to the application.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // UI components
    private EditText username;
    private EditText password;
    private Button signIn;
    private TextView registerTV;

    // Database handler and shared preferences
    private DBActivity DB;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    /**
     * Initializes the activity and sets up the UI components.
     *
     * @param savedInstanceState saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // LoginActivity is set to Purple theme by default
        setTheme(R.style.Theme_MyFirstApp_Purple);
        setContentView(R.layout.activity_login);

        initializeUIComponents();
    }

    /**
     * Initializes the UI components and listeners.
     */
    private void initializeUIComponents() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        signIn = findViewById(R.id.buttonsignin);
        signIn.setOnClickListener(this);

        registerTV = findViewById(R.id.goToRegisterBtn);
        registerTV.setOnClickListener(this);

        DB = new DBActivity(this);
    }

    /**
     * Handles click events of the UI components.
     *
     * @param view the clicked view component.
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonsignin:
                handleLogin();
                break;
            case R.id.goToRegisterBtn:
                redirectToRegistration();
                break;
        }
    }

    /**
     * Handles the login process.
     */
    private void handleLogin() {
        String user = username.getText().toString();
        String pass = password.getText().toString();

        if (areFieldsEmpty(user, pass)) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (areCredentialsValid(user, pass)) {
            proceedAfterSuccessfulLogin(user);
        } else {
            Toast.makeText(this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Checks if the provided credentials are empty.
     *
     * @param user the username.
     * @param pass the password.
     * @return true if one or both fields are empty, false otherwise.
     */
    private boolean areFieldsEmpty(String user, String pass) {
        return user.isEmpty() || pass.isEmpty();
    }

    /**
     * Validates the provided credentials with the database.
     *
     * @param user the username.
     * @param pass the password.
     * @return true if the credentials are valid, false otherwise.
     */
    private boolean areCredentialsValid(String user, String pass) {
        return DB.checkUserPassword(user, pass);
    }

    /**
     * Handles actions after a successful login.
     *
     * @param user the logged-in username.
     */
    private void proceedAfterSuccessfulLogin(String user) {
        String userTheme = DB.getUserTheme(user);

        saveUserPreferences(user, userTheme);

        Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

        redirectToMainActivity();
    }

    /**
     * Saves user's theme and login status to SharedPreferences.
     *
     * @param user      the username.
     * @param userTheme the user's theme.
     */
    private void saveUserPreferences(String user, String userTheme) {
        editor = getSharedPreferences("UserPreferences", MODE_PRIVATE).edit();
        editor.putString("theme", userTheme);
        editor.putString("username", user);
        editor.putBoolean("isLoggedIn", true);
        editor.apply();
    }

    /**
     * Redirects the user to the MainActivity.
     */
    private void redirectToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Redirects the user to the RegisterActivity.
     */
    private void redirectToRegistration() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}

