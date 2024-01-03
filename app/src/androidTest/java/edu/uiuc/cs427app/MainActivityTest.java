package edu.uiuc.cs427app;

import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.contrib.RecyclerViewActions;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.action.ViewActions.clearText;
import androidx.test.espresso.intent.Intents;

import org.junit.Before;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import edu.uiuc.cs427app.dao.SelectedCityDao;
import edu.uiuc.cs427app.data.SelectedCity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);
    
    // Set up mockDB.
    @Before
    public void setUp() throws Exception {
        //LoginActivity activity = activityRule.getActivity();
        Intents.init();
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

    @Test
    public void testAddCity() throws InterruptedException {

        SelectedCity testCity = new SelectedCity();
        testCity.cityName = "Chicago";

        // Value for Chicago.
        int testLocation = 1;

        // Click on button sign in.
        onView(withId(R.id.goToRegisterBtn)).perform(click());
        Thread.sleep(1000);

        // Attempt to register user either way.
        
        // Enter username.
        onView(withId(R.id.EditTextUsername)).perform(clearText(), typeText("guest1"));
        Thread.sleep(1000);

        // Enter password.
        onView(withId(R.id.EditTextPassword)).perform(clearText(), typeText("1234"));
        Thread.sleep(1000);

        // Press back button.
        Espresso Espresso = null;
        Espresso.pressBack();

        // Select pink UI.
        onView((withId(R.id.radioButtonPink))).perform(click());

        // Click register.
        onView((withId(R.id.ButtonRegister))).perform(click());

        // Either way, user is sent back to login page. Attempt login.
        
        // Enter username.
        onView(withId(R.id.username)).perform(clearText(), typeText("guest1"));
        Thread.sleep(1000);

        // Enter password.
        onView(withId(R.id.password)).perform(clearText(), typeText("1234"));
        Thread.sleep(1000);

        // Close keyboard.
        closeSoftKeyboard();
        Thread.sleep(1000);

        // Click on button sign in.
        onView(withId(R.id.buttonsignin)).perform(click());
        Thread.sleep(1000);

        // Click button to add location.
        onView(withId(R.id.buttonAddLocation)).perform(click());
        Thread.sleep(1000);

        // Click on the test location to add.
        onView(withId(R.id.RecycleViewCity)).perform(RecyclerViewActions.actionOnItemAtPosition(testLocation, click()));
        Thread.sleep(1000);

        // Initialize variables to access database and selectCities.
        AppDatabase db;
        SelectedCityDao selectedCityDao;
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").build();

        // Get values from the user list of cities.
        selectedCityDao = db.selectedCityDao();
        List<SelectedCity> newSelectedCities = selectedCityDao.getAll("guest1");

        // Iterate through list, assert when the city is found.
        for (SelectedCity city : newSelectedCities) {
            if(city.cityName.equals(testCity.cityName)) {
                assertEquals(city.cityName, testCity.cityName);
            }
        }

        // Click button to log off.
        onView(withId(R.id.logoutBtn)).perform(click());
        Thread.sleep(1000);
    }

    @Test
    public void testDeleteSelectedCityChampaign() throws InterruptedException {
        // Value for Champaign.
        int testLocation = 0;
        SelectedCity testCity = null;

        // login
        onView(withId(R.id.username)).perform(clearText(), typeText("guest1"));
        Thread.sleep(1000);
        onView(withId(R.id.password)).perform(clearText(), typeText("1234"));
        Thread.sleep(1000);
        closeSoftKeyboard();
        Thread.sleep(1000);
        onView(withId(R.id.buttonsignin)).perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.buttonAddLocation)).perform(click());
        Thread.sleep(1000);

        // Click on the test location to add.
        onView(withId(R.id.RecycleViewCity)).perform(RecyclerViewActions.actionOnItemAtPosition(testLocation, click()));
        Thread.sleep(2000);
        
        AppDatabase db;
        SelectedCityDao selectedCityDao;
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").build();

        selectedCityDao = db.selectedCityDao();
        List<SelectedCity> newSelectedCities = selectedCityDao.getAll("guest1");
        int itemCount_before = newSelectedCities.size();

        // Swipe left on the test location to remove.
        onView(withId(R.id.selectedCitiesRecycleView)).perform(RecyclerViewActions.actionOnItemAtPosition(testLocation, swipeLeft()));
        Thread.sleep(2000);

        selectedCityDao = db.selectedCityDao();
        newSelectedCities = selectedCityDao.getAll("guest1");
        int itemCount_after = newSelectedCities.size();
        int itemCount_diff = itemCount_before - itemCount_after;
        System.out.println("List count difference: " + itemCount_diff);
        assertEquals(1,itemCount_diff);
    }
}
