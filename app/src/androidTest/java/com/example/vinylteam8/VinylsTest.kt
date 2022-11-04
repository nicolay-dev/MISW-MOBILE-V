package com.example.vinylteam8




import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.util.TreeIterables
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.vinylteam8.ui.MainActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeoutException
import java.util.regex.Matcher


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
public class TestVinylsTeam8 {
    @Rule @JvmField
    val mActivityTestRule = ActivityScenarioRule(
        MainActivity::class.java
    )

        @Test
        fun test_isActivityInView() {
            onView(withId(R.id.main)).check(matches(isDisplayed()));
        }
        @Test
        fun test_AlbumsView() {
           val albumnav = onView(withId(R.id.navigation_album)).check(matches(isDisplayed()));
            albumnav.perform(click());
            onView(withId(R.id.albumsRv)).check(matches(isDisplayed()));
            Thread.sleep(5000)
            onView(withText("Buscando América")).check(matches(isDisplayed()));
        }





}
