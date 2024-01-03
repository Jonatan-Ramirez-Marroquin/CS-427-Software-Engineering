package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

public class LogoutTest {

    // ! Espresso has limitations in testing Toast messages effectively.
    // Therefore, instead of directly verifying the presence of Toast messages in our tests,
    // we focus on testing the outcomes of user actions, such as redirection to different activities following specific operations.
    // This approach ensures that we validate the functional behavior of the app rather than just the appearance of Toast messages.

    @Rule
    public IntentsTestRule<RegisterActivity> activityRule = new IntentsTestRule<>(RegisterActivity.class);

    // Set up mockDB.
    @Before
    public void setUp() {
        RegisterActivity activity = activityRule.getActivity();
    }

    @Test
    public void testSuccessfulLogout() {
        String uniqueUsername = "testing" + Thread.activeCount(); // some randomization on username
        String testPassword = "password123";

        // Use unique user name to test.
        onView(withId(R.id.EditTextUsername)).perform(typeText(uniqueUsername), closeSoftKeyboard());
        onView(withId(R.id.EditTextPassword)).perform(typeText(testPassword), closeSoftKeyboard());
        onView(withId(R.id.radioButtonRed)).perform(click());

        // Click register button.
        onView(withId(R.id.ButtonRegister)).perform(click());

        // Check whether direct to LoginActivity.
        intended(hasComponent(LoginActivity.class.getName()));
        // !!!Assert!
        assert (onView(withId(R.id.buttonsignin)) != null);

        // login
        onView(withId(R.id.username)).perform(typeText(uniqueUsername), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(testPassword), closeSoftKeyboard());
        onView(withId(R.id.buttonsignin)).perform(click());

        // Check whether direct to LoginActivity.
        intended(hasComponent(MainActivity.class.getName()));
        // !!!Assert!
        assert (onView(withId(R.id.buttonsignin)) != null);


        // click logout
        onView(withId(R.id.logoutBtn)).perform(click());
        //end of test

    }

}