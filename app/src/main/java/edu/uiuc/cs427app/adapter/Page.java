package edu.uiuc.cs427app.adapter;

/**
 * Enum representing the different pages that can be navigated to in the application.
 * This is used to direct the flow of the application to the correct activity based on user actions.
 */
public enum Page {
    /**
     * Represents the page that displays the detailed weather information.
     */
    WeatherDetail,

    /**
     * Represents the page that displays the map view.
     */
    Map,

    /**
     * Represents a state where no navigation is required.
     */
    None
}
