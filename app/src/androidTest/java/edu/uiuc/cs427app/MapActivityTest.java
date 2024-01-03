package edu.uiuc.cs427app;

import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MapActivityTest {

    @Test
    public void testWebViewLoadingWithChampaignData() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MapActivity.class);
        intent.putExtra("city", "Champaign");
        intent.putExtra("latitude", "40.1164");
        intent.putExtra("longitude", "-88.2434");

        ActivityScenario<MapActivity> scenario = ActivityScenario.launch(intent);
        CountDownLatch latch = new CountDownLatch(1);

        scenario.onActivity(activity -> {
            TextView cityTextView = activity.findViewById(R.id.cityTextView);
            TextView longLatTextView = activity.findViewById(R.id.longLatTextView);
            WebView webView = activity.findViewById(R.id.webview);

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    webView.evaluateJavascript(
                            "(function() { return document.querySelector('iframe').src; })();",
                            value -> {
                                String iframeSrc = value.replace("\"", "").replace("\\\\", "\\");
                                assertEquals("https://maps.google.com/maps?q=40.1164,-88.2434&t=&z=15&ie=UTF8&iwloc=&output=embed", iframeSrc);
                                latch.countDown();
                            }
                    );
                }
            });

            assertEquals("Champaign", cityTextView.getText().toString());
            assertEquals("Latitude: 40.1164 longitude: -88.2434", longLatTextView.getText().toString());
            assertNotNull(webView);
            assertTrue(webView.getSettings().getJavaScriptEnabled());
        });

        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void testWebViewLoadingWithNewYorkData() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MapActivity.class);
        intent.putExtra("city", "New York");
        intent.putExtra("latitude", "40.7128");
        intent.putExtra("longitude", "-74.0060");

        ActivityScenario<MapActivity> scenario = ActivityScenario.launch(intent);
        CountDownLatch latch = new CountDownLatch(1);

        scenario.onActivity(activity -> {
            TextView cityTextView = activity.findViewById(R.id.cityTextView);
            TextView longLatTextView = activity.findViewById(R.id.longLatTextView);
            WebView webView = activity.findViewById(R.id.webview);

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    webView.evaluateJavascript(
                            "(function() { return document.querySelector('iframe').src; })();",
                            value -> {
                                String iframeSrc = value.replace("\"", "").replace("\\\\", "\\");
                                assertEquals("https://maps.google.com/maps?q=40.7128,-74.0060&t=&z=15&ie=UTF8&iwloc=&output=embed", iframeSrc);
                                latch.countDown();
                            }
                    );
                }
            });

            assertEquals("New York", cityTextView.getText().toString());
            assertEquals("Latitude: 40.7128 longitude: -74.0060", longLatTextView.getText().toString());
            assertNotNull(webView);
            assertTrue(webView.getSettings().getJavaScriptEnabled());
        });

        latch.await(5, TimeUnit.SECONDS);
    }
}
