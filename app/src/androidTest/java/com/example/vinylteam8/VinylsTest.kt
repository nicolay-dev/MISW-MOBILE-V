package com.example.vinylteam8

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.vinylteam8.ui.MainActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.not

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class TestVinylsTeam8 {
    @Rule @JvmField
    val mActivityTestRule = ActivityScenarioRule(
        MainActivity::class.java
    )

        @Test
        fun test_isActivityInView() {
            onView(withId(R.id.main)).check(matches(isDisplayed()))
        }

        @Test
        fun test_AlbumsView() {
           val albumnav = onView(withId(R.id.navigation_album)).check(matches(isDisplayed()))
            albumnav.perform(click())
            onView(withId(R.id.albumsRv)).check(matches(isDisplayed()))
            Thread.sleep(10000)
            onView(withText("Buscando América")).check(matches(isDisplayed()))
        }

        @Test
        fun test_ArtistView() {
            val artistav = onView(withId(R.id.navigation_performer)).check(matches(isDisplayed()))
            artistav.perform(click())
            onView(withId(R.id.perfomerRv)).check(matches(isDisplayed()))
            Thread.sleep(10000)
            onView(withText("Soundgarden")).check(matches(isDisplayed()))
        }

        @Test
        fun test_Artist_Album() {
            val albumnav = onView(withId(R.id.navigation_album)).check(matches(isDisplayed()))
            albumnav.perform(click())
            Thread.sleep(10000)
            onView(withId(R.id.albumsRv)).check(matches(isDisplayed()))
            onView(withText("Poeta del pueblo")).check(matches(isDisplayed()))
            val artistav = onView(withId(R.id.navigation_performer)).check(matches(isDisplayed()))
            artistav.perform(click())
            Thread.sleep(10000)
            onView(withId(R.id.perfomerRv)).check(matches(isDisplayed()))
            onView(withText("AC/DC")).check(matches(isDisplayed()))
        }



    @Test
    fun test_Album_Detail() {
        val albumnav = onView(withId(R.id.navigation_album)).check(matches(isDisplayed()))
        albumnav.perform(click())
        Thread.sleep(10000)
        onView(withId(R.id.albumsRv)).check(matches(isDisplayed()))
        onView(withText("Poeta del pueblo")).perform(click())
        Thread.sleep(3000)
        onView(withId(R.id.textView6)).check(matches(not(withText(""))))
    }

    @Test
    fun test_Artist_Detail() {
        val artistav = onView(withId(R.id.navigation_performer)).check(matches(isDisplayed()))
        artistav.perform(click())
        onView(withId(R.id.perfomerRv)).check(matches(isDisplayed()))
        Thread.sleep(10000)
        onView(withText("Queen")).perform(click())
        Thread.sleep(3000)
        onView(withId(R.id.textView6)).check(matches(not(withText(""))))
    }



    @Test
    fun test_CollectorView() {
        val collectorav = onView(withId(R.id.navigation_collector)).check(matches(isDisplayed()))
        collectorav.perform(click())
        onView(withId(R.id.collectorsRv)).check(matches(isDisplayed()))
        Thread.sleep(10000)
        onView(withText("Manolo Bellon")).check(matches(isDisplayed()))
    }

    @Test
    fun test_Artist_Detail_Album_Detail_Collector_View() {
        val albumnav = onView(withId(R.id.navigation_album)).check(matches(isDisplayed()))
        albumnav.perform(click())
        Thread.sleep(10000)
        onView(withId(R.id.albumsRv)).check(matches(isDisplayed()))
        onView(withText("A Night at the Opera")).perform(click())
        Thread.sleep(3000)
        onView(withId(R.id.textView6)).check(matches(not(withText(""))))
        onView(withContentDescription("Navigate up"))
        Thread.sleep(4000)
        val artistav = onView(withId(R.id.navigation_performer)).check(matches(isDisplayed()))
        artistav.perform(click())
        onView(withId(R.id.perfomerRv)).check(matches(isDisplayed()))
        Thread.sleep(10000)
        onView(withText("Rubén Blades Bellido de Luna")).perform(click())
        Thread.sleep(3000)
        onView(withId(R.id.textView6)).check(matches(not(withText(""))))
        onView(withContentDescription("Navigate up"))
        Thread.sleep(4000)
        val collectorav = onView(withId(R.id.navigation_collector)).check(matches(isDisplayed()))
        collectorav.perform(click())
        onView(withId(R.id.collectorsRv)).check(matches(isDisplayed()))
        Thread.sleep(10000)
        onView(withText("Jaime Monsalve")).check(matches(isDisplayed()))
    }
}
