package edu.uiuc.cs427app;

import android.content.SharedPreferences;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * RegisterActivity: An activity allowing users to register for the application.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText usernameTextField;
    private EditText passwordTextField;
    private DBActivity DB;

    // For Register Test .
    public void setDBActivity(DBActivity dbActivity) {
        this.DB = dbActivity;
    }


    /**
     * Initializes the activity and sets up the UI components.
     *
     * @param savedInstanceState saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeUIComponents();

        // Clear SharedPreferences' isLoggedIn value
        clearLoginStatus();
    }

    /**
     * Initializes the UI components and listeners.
     */
    private void initializeUIComponents() {
        usernameTextField = findViewById(R.id.EditTextUsername);
        passwordTextField = findViewById(R.id.EditTextPassword);
        Button buttonRegister = findViewById(R.id.ButtonRegister);
        TextView goToLoginText = findViewById(R.id.goToLoginBtn);

        buttonRegister.setOnClickListener(this);
        goToLoginText.setOnClickListener(this);

        DB = new DBActivity(this);
    }

    /**
     * Clears the user's login status in shared preferences.
     */
    private void clearLoginStatus() {
        SharedPreferences.Editor editor = getSharedPreferences("UserPreferences", MODE_PRIVATE).edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();
    }

    /**
     * Handles click events of the UI components.
     *
     * @param view the clicked view component.
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ButtonRegister:
                createUser();
                break;
            case R.id.goToLoginBtn:
                redirectToLogin();
                break;
        }
    }

    /**
     * Redirects the user to the LoginActivity.
     */
    private void redirectToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Handles user registration based on the provided credentials.
     */
    void createUser() {
        String user = usernameTextField.getText().toString();
        String pass = passwordTextField.getText().toString();
        String selectedTheme = retrieveSelectedTheme();

        if (!validateInput(user, pass)) {
            return;
        }

        handleRegistration(user, pass, selectedTheme);
    }

    /**
     * Retrieves the selected theme from the radio group.
     *
     * @return the string representation of the selected theme.
     */
    private String retrieveSelectedTheme() {
        String selectedTheme = "Theme.MyFirstApp.Purple";
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        int selectedId = radioGroup.getCheckedRadioButtonId();

        switch (selectedId) {
            case R.id.radioButtonPurple:
                selectedTheme = "Theme.MyFirstApp.Purple";
                break;
            case R.id.radioButtonPink:
                selectedTheme = "Theme.MyFirstApp.Pink";
                break;
            case R.id.radioButtonRed:
                selectedTheme = "Theme.MyFirstApp.Red";
                break;
        }
        return selectedTheme;
    }

    /**
     * Validates user input and shows appropriate error messages.
     *
     * @param user the username.
     * @param pass the password.
     * @return true if the input is valid; otherwise, false.
     */
    private boolean validateInput(String user, String pass) {
        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!user.matches("^[a-zA-Z0-9]+$")) {
            Toast.makeText(this, "Invalid username. Should contain only letters and numbers.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (user.length() < 4) {
            Toast.makeText(this, "Invalid username. Should be more than 3 characters.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Handles the user registration process and shows appropriate messages based on the result.
     *
     * @param user          the username.
     * @param pass          the password.
     * @param selectedTheme the selected theme.
     */
    private void handleRegistration(String user, String pass, String selectedTheme) {
        int checkRegister = DB.insertData(user, pass, selectedTheme);

        switch (checkRegister) {
            case 1:
                Toast.makeText(this, "User already exists. Please login.", Toast.LENGTH_SHORT).show();
                redirectToLogin();
                break;
            case 2:
                Toast.makeText(this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                redirectToLogin();
                break;
        }
    }
}

