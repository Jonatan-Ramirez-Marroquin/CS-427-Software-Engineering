package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

// T3: DetailsActivity extends ThemeActivity for onCreate() function
/**
 * DetailsActivity: An activity that shows detailed information about a city's weather.
 * This activity extends ThemeActivity to inherit its theme setting behavior.
 */
public class DetailsActivity extends ThemeActivity implements View.OnClickListener{

    // UI components
    private TextView welcomeMessage;
    private TextView cityInfoMessage;
    private TextView latLongTextView;
    private TextView dateTimeTextView;
    private TextView tempTextView;
    private TextView weatherTextView;
    private TextView humidityTextView;
    private TextView windConditionTextView;
    private Button buttonMap;

    private String longitude;
    private String latitude;
    private  String cityName;

    /**
     * Initializes the activity, sets the content view, and processes the intent data.
     *
     * @param savedInstanceState saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        longitude = getIntent().getStringExtra("longitude");
        latitude = getIntent().getStringExtra("latitude");

        initializeUIComponents();
        try {
            displayCityInformation();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initializes the UI components and sets up the necessary listeners.
     */
    private void initializeUIComponents() {
        welcomeMessage = findViewById(R.id.welcomeText);
        cityInfoMessage = findViewById(R.id.cityInfo);
        latLongTextView = findViewById(R.id.latLongTextView);
        dateTimeTextView = findViewById(R.id.dateTimeTextView);
        tempTextView = findViewById(R.id.tempTextView);
        weatherTextView = findViewById(R.id.weatherTextView);
        humidityTextView = findViewById(R.id.humidityTextView);
        windConditionTextView = findViewById(R.id.windConditionTextView);


        buttonMap = findViewById(R.id.mapButton);
        buttonMap.setOnClickListener(this);
    }

    /**
     * Retrieves city data from the intent payload and acquires the weather information from the openweathermap API.
     */
    private void displayCityInformation() throws IOException, JSONException {
        cityName = getIntent().getStringExtra("city");
        if (cityName != null) {
            String welcome = "Welcome to " + cityName;
            String cityWeatherInfo = "Detailed weather information from " + cityName;

            welcomeMessage.setText(welcome);
            cityInfoMessage.setText(cityWeatherInfo);

            final String API_KEY = "b53484e2a4a9b0f815372c00b8590e8d";
            longitude = getIntent().getStringExtra("longitude");
            latitude = getIntent().getStringExtra("latitude");
            String urlString = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY;
            System.out.println(urlString);

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            System.out.println(conn);

            conn.setRequestMethod("GET");


            new Thread(new Runnable() {
                public void run() {
                    int responsecode = 0;
                    try {
                        conn.connect();
                        responsecode = conn.getResponseCode();
                        System.out.println(responsecode);

                        String jsonStr;

                        if (responsecode != 200) {
                            throw new RuntimeException("HttpResponseCode: " + responsecode);
                        } else {
                            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            String inputLine;
                            StringBuffer content = new StringBuffer();
                            while ((inputLine = in.readLine()) != null) {
                                content.append(inputLine);
                            }
                            in.close();
                            conn.disconnect();
                            jsonStr = content.toString();
                            System.out.println("Output: " + jsonStr);
                            updateTextViews(jsonStr);
                        }

                        conn.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    /**
     * Displays the relevant weather information for a city by updating the TextView.
     * Inputs: a String object with the json response from the API call.
     * Displays the following information:
     *     The date and time (local to the city)
     *     The temperature in both Celsius and Fahrenheit
     *     The weather condition as a string (Such as "Cloudy", "Windy", "Rainy", or "Sunny")
     *     The humidity (as % of water vapor present in the air)
     *     The wind condition (as miles/hour)
     */
    private void updateTextViews(String jsonStr) throws JSONException {
        JSONObject obj = new JSONObject(jsonStr);

        double lon = obj.getJSONObject("coord").getDouble("lon");
        double lat = obj.getJSONObject("coord").getDouble("lat");
        String weather = obj.getJSONArray("weather").getJSONObject(0).getString("main");
        double temp = obj.getJSONObject("main").getDouble("temp");
        double humidity = obj.getJSONObject("main").getDouble("humidity");
        double windSpeed = obj.getJSONObject("wind").getDouble("speed");
        // API returns the date/time as the amount of seconds since January 1, 1970.
        long msSince1970 = obj.getLong("dt") * 1000L;
        // API returns "timezone" as shift in seconds from UTC
        long msOffset = obj.getLong("timezone") * 1000L;

        Date localDateTime = new Date(msSince1970 + msOffset);
        SimpleDateFormat monthDayYear = new SimpleDateFormat("MM/dd/yyyy");
        monthDayYear.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat hourMinSec = new SimpleDateFormat("HH:mm:ss");
        hourMinSec.setTimeZone(TimeZone.getTimeZone("UTC"));

        //The API returns the temperature in Kelvin, so we will need to convert to Celsius and Fahrenheit.
        double tempC = temp - 273.15;
        double tempF = (1.8*temp) - 459.67;
        //The API returns wind speed in meter/sec. Convert to Miles/Hour which is common unit to display
        windSpeed = 2.23693629205 * windSpeed;
        //Rounding temp, humidity, and wind speed to 2 decimal places for readability
        latLongTextView.setText("Longitude: ("+lon+") and Latitude: ("+lat+")");
        dateTimeTextView.setText("Date: " + monthDayYear.format(localDateTime) + " Time: " + hourMinSec.format(localDateTime));
        tempTextView.setText("Temperature : " + String.format("%.2f", tempC) + "°C ; " + String.format("%.2f", tempF) + "°F");
        weatherTextView.setText("Weather: "+ weather);
        humidityTextView.setText("Humidity: "+String.format("%.2f", humidity)+"%");
        //NOTE: "wind condition" is not specified in proj requirements. Should ask what this entails
        windConditionTextView.setText("Wind Speed: "+ String.format("%.2f", windSpeed) +" Miles/Hour");
    }

    /**
     * Handles the button click to navigate to the map view.
     *
     * @param view the clicked view component.
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.mapButton) {
            navigateToMapView();
        }
    }

    /**
     * Navigates to an activity that displays the map.
     */
    private void navigateToMapView() {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("longitude", longitude);
        intent.putExtra("latitude", latitude);
        intent.putExtra("city", cityName);
        startActivity(intent);
    }
}


