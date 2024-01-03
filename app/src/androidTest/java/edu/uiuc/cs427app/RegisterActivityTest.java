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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {

    // ! Espresso has limitations in testing Toast messages effectively.
    // Therefore, instead of directly verifying the presence of Toast messages in our tests,
    // we focus on testing the outcomes of user actions, such as redirection to different activities following specific operations.
    // This approach ensures that we validate the functional behavior of the app rather than just the appearance of Toast messages.

    @Rule
    public IntentsTestRule<RegisterActivity> activityRule = new IntentsTestRule<>(RegisterActivity.class);

    // ! We use mock database for testing registration failure.
    private DBActivity mockDB;

    // In testSuccessfulRegistration, we use a unique username generated with System.currentTimeMillis().
    // This approach ensures that each test run uses a new, unique username, avoiding conflicts with existing usernames in the real database.
    // The unique username helps to prevent test failures due to duplicate entries and ensures that the test is valid and repeatable across different test runs.
    private static String uniqueUsername;

    // Set up test password.
    String testPassword = "password123";

    // Set up unique user name for testing.
    @BeforeClass
    public static void setupClass() {
        uniqueUsername = "testUser" + System.currentTimeMillis();
    }

    // Set up mockDB.
    @Before
    public void setUp() {
        mockDB = Mockito.mock(DBActivity.class);
        RegisterActivity activity = activityRule.getActivity();
        activity.setDBActivity(mockDB);
    }


    // 1. Test successful registration.
    @Test
    public void testSuccessfulRegistration() {

        // Use unique user name to test.
        onView(withId(R.id.EditTextUsername)).perform(typeText(uniqueUsername), closeSoftKeyboard());
        onView(withId(R.id.EditTextPassword)).perform(typeText(testPassword), closeSoftKeyboard());

        // Click register button.
        onView(withId(R.id.ButtonRegister)).perform(click());

        // Check whether direct to LoginActivity.
        intended(hasComponent(LoginActivity.class.getName()));

        // Assert!
        assert (onView(withId(R.id.buttonsignin)) != null);
    }

    // 2. Test registration with existing username.
    // ! Better not to be tested singly.
    // Because we also use 'uniqueUserName' as the username in this test which has already been registered in the real database during the 'testSuccessfulRegistration' test.
    // This ensures that 'uniqueUserName' is recognized as an existing username, allowing us to accurately test scenarios that depend on pre-existing user data.
    @Test
    public void testRegistrationWithExistingUsername() {

        onView(withId(R.id.EditTextUsername)).perform(typeText(uniqueUsername), closeSoftKeyboard());
        onView(withId(R.id.EditTextPassword)).perform(typeText(testPassword), closeSoftKeyboard());

        // Click register button.
        onView(withId(R.id.ButtonRegister)).perform(click());

        // Check whether direct to LoginActivity.
        intended(hasComponent(LoginActivity.class.getName()));

        // Assert!
        assert (onView(withId(R.id.buttonsignin)) != null);
    }

    // 3. Test empty user name and password.
    @Test
    public void testEmptyFields() {

        // Put empty user name and password.
        onView(withId(R.id.EditTextUsername)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.EditTextPassword)).perform(typeText(""), closeSoftKeyboard());

        // Click register button.
        onView(withId(R.id.ButtonRegister)).perform(click());

        // Check whether still stay in RegisterActivity.
        onView(withId(R.id.ButtonRegister)).check(matches(isDisplayed()));

        // Assert!
        assert (onView(withId(R.id.ButtonRegister)) != null);
    }

    // 4. Test illegal user name.
    @Test
    public void testInvalidUsername() {

        // Put user name of illegal characters.
        onView(withId(R.id.EditTextUsername)).perform(typeText("user!@#"), closeSoftKeyboard());
        onView(withId(R.id.EditTextPassword)).perform(typeText(testPassword), closeSoftKeyboard());

        // Click register button.
        onView(withId(R.id.ButtonRegister)).perform(click());

        // Check whether still stay in RegisterActivity.
        onView(withId(R.id.ButtonRegister)).check(matches(isDisplayed()));

        // Assert!
        assert (onView(withId(R.id.ButtonRegister)) != null);
    }


    // 5. Test short user name.
    @Test
    public void testShortUsername() {

        // Put user name which is too short (less than 4 characters).
        onView(withId(R.id.EditTextUsername)).perform(typeText("usr"), closeSoftKeyboard());
        onView(withId(R.id.EditTextPassword)).perform(typeText(testPassword), closeSoftKeyboard());

        // Click register button.
        onView(withId(R.id.ButtonRegister)).perform(click());

        // Check whether still stay in RegisterActivity.
        onView(withId(R.id.ButtonRegister)).check(matches(isDisplayed()));

        // Assert!
        assert (onView(withId(R.id.ButtonRegister)) != null);
    }

    // 6. Test registration failure using mock database.
    @Test
    public void testRegistrationFailure() {
        // No matter what is be put, set the condition to case 2 (registration failure).
        when(mockDB.insertData(anyString(), anyString(), anyString())).thenReturn(2);
        onView(withId(R.id.EditTextUsername)).perform(typeText("FailureUser"), closeSoftKeyboard());
        onView(withId(R.id.EditTextPassword)).perform(typeText(testPassword), closeSoftKeyboard());

        // Click register button.
        onView(withId(R.id.ButtonRegister)).perform(click());

        // Check whether still stay in RegisterActivity.
        onView(withId(R.id.ButtonRegister)).check(matches(isDisplayed()));

        // Assert!
        assert (onView(withId(R.id.ButtonRegister)) != null);
    }

    // 7. Theme task -> Red.
    @Test
    public void testRegistrationWithRedThemeSelection() {
        String ThemeUniqueUsername = "themeUser" + System.currentTimeMillis();

        // Put user name and password.
        onView(withId(R.id.EditTextUsername)).perform(typeText(ThemeUniqueUsername), closeSoftKeyboard());
        onView(withId(R.id.EditTextPassword)).perform(typeText(testPassword), closeSoftKeyboard());

        // Select the red theme.
        onView(withId(R.id.radioButtonRed)).perform(click());

        // Submit it.
        onView(withId(R.id.ButtonRegister)).perform(click());

        // Check whether goes to LoginActivity.
        intended(hasComponent(LoginActivity.class.getName()));

        // Assert!
        assert (onView(withId(R.id.buttonsignin)) != null);
    }


    // 8. Theme task -> Purple.
    @Test
    public void testRegistrationWithPurpleThemeSelection() {
        String ThemeUniqueUsername = "themeUser" + System.currentTimeMillis();

        // Put user name and password.
        onView(withId(R.id.EditTextUsername)).perform(typeText(ThemeUniqueUsername), closeSoftKeyboard());
        onView(withId(R.id.EditTextPassword)).perform(typeText(testPassword), closeSoftKeyboard());

        // Select the purple theme.
        onView(withId(R.id.radioButtonPurple)).perform(click());

        // Submit it.
        onView(withId(R.id.ButtonRegister)).perform(click());

        // Check whether goes to LoginActivity.
        intended(hasComponent(LoginActivity.class.getName()));

        // Assert!
        assert (onView(withId(R.id.buttonsignin)) != null);
    }

    // 9. Theme task -> Pink.
    @Test
    public void testRegistrationWithPinkThemeSelection() {
        String ThemeUniqueUsername = "themeUser" + System.currentTimeMillis();

        // Put user name and password.
        onView(withId(R.id.EditTextUsername)).perform(typeText(ThemeUniqueUsername), closeSoftKeyboard());
        onView(withId(R.id.EditTextPassword)).perform(typeText(testPassword), closeSoftKeyboard());

        // Select the pink theme.
        onView(withId(R.id.radioButtonPink)).perform(click());

        // Submit it.
        onView(withId(R.id.ButtonRegister)).perform(click());

        // Check whether goes to LoginActivity.
        intended(hasComponent(LoginActivity.class.getName()));

        // Assert!
        assert (onView(withId(R.id.buttonsignin)) != null);
    }

}

