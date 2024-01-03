package edu.uiuc.cs427app.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents a selected city by a user in the database.
 * This entity holds the details of a city selected by a specific user.
 */
@Entity
public class SelectedCity {

    // Unique identifier for each selected city record
    @PrimaryKey(autoGenerate = true)
    public int uid;

    // Username of the user who selected the city
    @ColumnInfo(name = "username")
    public String username;

    // Name of the selected city
    @ColumnInfo(name = "city_name")
    public String cityName;

    // Geographical latitude of the selected city
    @ColumnInfo(name = "latitude")
    public String latitude;

    // Geographical longitude of the selected city
    @ColumnInfo(name = "longitude")
    public String longitude;
}
