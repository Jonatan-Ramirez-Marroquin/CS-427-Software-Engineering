package edu.uiuc.cs427app.models;

/**
 * Represents a city with its name and geographical coordinates.
 */
public class City {

    // Name of the city
    private final String cityName;

    // Geographical longitude of the city
    private final String longitude;

    // Geographical latitude of the city
    private final String latitude;

    /**
     * Constructor to initialize a City object.
     *
     * @param cityName  The name of the city.
     * @param longitude The geographical longitude of the city.
     * @param latitude  The geographical latitude of the city.
     */
    public City(String cityName, String longitude, String latitude) {
        this.cityName = cityName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Retrieves the name of the city.
     *
     * @return The name of the city.
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Retrieves the geographical longitude of the city.
     *
     * @return The longitude of the city.
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Retrieves the geographical latitude of the city.
     *
     * @return The latitude of the city.
     */
    public String getLatitude() {
        return latitude;
    }
}


