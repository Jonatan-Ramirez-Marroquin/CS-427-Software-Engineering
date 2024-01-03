package edu.uiuc.cs427app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Intent;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class WeatherActivityTest {

    @Test
    public void testWeatherChampaign() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), DetailsActivity.class);
        intent.putExtra("city", "Champaign");
        intent.putExtra("latitude", "40.1164");
        intent.putExtra("longitude", "-88.2434");

        ActivityScenario<DetailsActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            TextView cityInfoView = activity.findViewById(R.id.cityInfo);
            TextView latLongTextView = activity.findViewById(R.id.latLongTextView);
            assertEquals("Detailed weather information from Champaign", cityInfoView.getText().toString());
            assertEquals("Longitude: (-88.2434) and Latitude: (40.1164)", latLongTextView.getText().toString());
            latch.countDown();
        });
        latch.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void testWeatherNewYork() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), DetailsActivity.class);
        intent.putExtra("city", "New York");
        intent.putExtra("latitude", "40.7128");
        intent.putExtra("longitude", "-74.0059");

        ActivityScenario<DetailsActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            TextView cityInfoView = activity.findViewById(R.id.cityInfo);
            TextView latLongTextView = activity.findViewById(R.id.latLongTextView);
            assertEquals("Detailed weather information from New York", cityInfoView.getText().toString());
            assertEquals("Longitude: (-74.0059) and Latitude: (40.7128)", latLongTextView.getText().toString());
            latch.countDown();
        });
        latch.await(10, TimeUnit.SECONDS);
    }
}
