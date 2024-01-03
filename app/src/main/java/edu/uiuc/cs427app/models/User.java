package edu.uiuc.cs427app.models;

/**
 * Represents a user with login credentials and theme preferences.
 */
public class User {

    // User name of the user
    private final String userName;

    // Password of the user
    private final String password;

    // ID representing the chosen theme for the user
    private int chosenThemeId;

    /**
     * Constructs a new User object with the given parameters.
     *
     * @param userName     The user's name.
     * @param password     The user's password.
     * @param chosenThemeId The ID representing the user's chosen theme.
     */
    public User(String userName, String password, int chosenThemeId) {
        this.userName = userName;
        this.password = password;
        this.chosenThemeId = chosenThemeId;
    }

    /**
     * Retrieves the user's name.
     *
     * @return The user's name.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Retrieves the user's password.
     *
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the ID representing the user's chosen theme.
     *
     * @return The ID of the user's chosen theme.
     */
    public int getChosenThemeId() {
        return chosenThemeId;
    }

    /**
     * Updates the ID representing the user's chosen theme.
     *
     * @param chosenThemeId The new ID of the chosen theme.
     */
    public void setChosenThemeId(int chosenThemeId) {
        this.chosenThemeId = chosenThemeId;
    }
}


