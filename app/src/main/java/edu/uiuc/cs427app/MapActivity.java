package edu.uiuc.cs427app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * Activity that displays a map location based on passed longitude and latitude.
 * This activity retrieves the location data from the intent that started it and
 * shows a WebView with a Google Maps iframe pointing to the specified location.
 */
public class MapActivity extends ThemeActivity {

    private TextView cityTextView;
    private TextView longLatTextView;

    private String longitude;
    private String latitude;
    private String cityName;

    /**
     * Called when the activity is starting. Initializes the user interface and map loading process.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initializeUIComponents();
        retrieveIntentData();
        updateLongLatTextView();
        loadWebViewMap();
    }

    /**
     * Initializes the UI components of the activity.
     */
    private void initializeUIComponents() {
        cityTextView = findViewById(R.id.cityTextView);
        longLatTextView = findViewById(R.id.longLatTextView);
        cityTextView = findViewById(R.id.cityTextView);
    }

    /**
     * Retrieves the intent data passed to this activity, which includes the city name,
     * longitude, and latitude values to be displayed on the map.
     */
    private void retrieveIntentData() {
        longitude = getIntent().getStringExtra("longitude");
        latitude = getIntent().getStringExtra("latitude");
        cityName = getIntent().getStringExtra("city");
    }

    /**
     * Updates the TextViews to display the name of the city and its coordinates.
     */
    private void updateLongLatTextView() {
        cityTextView.setText(cityName);
        longLatTextView.setText("Latitude: " + latitude + " longitude: " + longitude);
    }

    /**
     * Loads the map into a WebView by embedding an iframe of Google Maps with the given coordinates.
     * JavaScript is enabled for WebView to properly load the Google Maps interface.
     */
    private void loadWebViewMap() {
        WebView webView;

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript

        // Make sure to enable DomStorage if you haven't already
        webView.getSettings().setDomStorageEnabled(true);
        // HTML content with the iframe including the Google Maps URL
        String iframeContent = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "body, html { height: 100%; margin: 0; padding: 0; }" +
                "iframe { height: 100%; width: 100%; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<iframe src=\"https://maps.google.com/maps?q=" +
                latitude + "," + longitude +
                "&t=&z=15&ie=UTF8&iwloc=&output=embed\" " +
                "frameborder=\"0\" style=\"border:0\" allowfullscreen></iframe>" +
                "</body></html>";


        // Load the HTML content into the WebView
        webView.loadData(iframeContent, "text/html", "UTF-8");


    }
}
