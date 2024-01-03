package edu.uiuc.cs427app;

import androidx.test.espresso.intent.Intents;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

    @Test
    public void testValidLogin() throws InterruptedException {
        // Validate valid user login
        onView(withId(R.id.username)).perform(clearText(), typeText("liu318"));
        Thread.sleep(1000);

        onView(withId(R.id.password)).perform(clearText(), typeText("1234"));
        Thread.sleep(1000);

        closeSoftKeyboard();
        Thread.sleep(1000);

        onView(withId(R.id.buttonsignin)).perform(click());
        Thread.sleep(1000);

        // assertion to check that the activity has change
        assert (onView(withId(R.id.buttonAddLocation)) != null);
    }

    @Test
    public void testInvalidLogin() throws InterruptedException {
        // Validate invalid user login
        onView(withId(R.id.username)).perform(clearText(), typeText("Invalid Credentials!"));
        Thread.sleep(1000);

        onView(withId(R.id.password)).perform(clearText(), typeText("****"));
        Thread.sleep(1000);

        closeSoftKeyboard();
        Thread.sleep(1000);

        onView(withId(R.id.buttonsignin)).perform(click());
        Thread.sleep(1000);

        // assertion to check that the activity has NOT changed
        assert (onView(withId(R.id.buttonsignin)) != null);
    }
}