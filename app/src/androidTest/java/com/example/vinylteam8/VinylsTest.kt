package com.example.vinylteam8

import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.vinylteam8.ui.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf

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
    fun test_Collector_Detail() {
        val collectorav = onView(withId(R.id.navigation_collector)).check(matches(isDisplayed()))
        collectorav.perform(click())
        onView(withId(R.id.collectorsRv)).check(matches(isDisplayed()))
        Thread.sleep(10000)
        onView(withText("Manolo Bellon")).perform(click())
        Thread.sleep(3000)
        onView(withId(R.id.textView16)).check(matches(not(withText(""))))
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




    @Test
    fun test_Album_Create() {
        val materialButton = onView(
            Matchers.allOf(
                withId(R.id.create_album_button), withText("Crear"),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val textInputEditText = onView(
            Matchers.allOf(
                withId(R.id.txt_post_name),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText.perform(scrollTo(), replaceText("Blanco"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            Matchers.allOf(
                withId(R.id.txt_post_image),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText2.perform(scrollTo(), replaceText("https://pxccdn.ciudadano.news/ciudadano/072020/1596209333685/blanco.jpg"), closeSoftKeyboard())

        val textInputEditText3 = onView(
            Matchers.allOf(
                withId(R.id.txt_post_description),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText3.perform(scrollTo(), replaceText("Blanco es el decimoquinto álbum de estudio del cantautor guatemalteco Ricardo Arjona. Fue lanzado al mercado el 29 de mayo de 2020. El álbum se caracteriza por el estilo clásico de sus letras, con una fusión de ritmos entre el pop, la balada romántica, el tango, el jazz, el blues y el rock"), closeSoftKeyboard())

        val textInputEditText4 = onView(
            Matchers.allOf(
                withId(R.id.txt_post_releaseDate),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                )
            )
        )
        textInputEditText4.perform(scrollTo(), replaceText("05/08/2021"), closeSoftKeyboard())

              val materialButton2 = onView(
            Matchers.allOf(
                withId(R.id.post_button), withText("Crear"),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        13
                    ),
                    2
                )
            )
        )
        materialButton2.perform(scrollTo(), click())

        onView(withText("Álbum agregado.."))
            .inRoot(ToastMatcher().apply {
                matches(isDisplayed())
            });
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }


    @Test
    fun track_create() {
            val albumnav = onView(withId(R.id.navigation_album)).check(matches(isDisplayed()))
            albumnav.perform(click())
            Thread.sleep(10000)
            onView(withId(R.id.albumsRv)).check(matches(isDisplayed()))
        val recyclerView = onView(
            Matchers.allOf(
                withId(R.id.albumsRv),
                childAtPosition(
                    withClassName(Matchers.`is`("android.widget.ScrollView")),
                    0
                )
            )
        )
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                click()
            )
        )
        Thread.sleep(5000)
        val materialButton = onView(
            Matchers.allOf(
                withId(R.id.create_track_button), withText("Crear Track"),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("android.widget.ScrollView")),
                        0
                    ),
                    2
                )
            )
        )
        materialButton.perform(scrollTo(), click())

        val textInputEditText = onView(
            Matchers.allOf(
                withId(R.id.txt_track_name),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("Belen"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            Matchers.allOf(
                withId(R.id.txt_track_duration),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("4:44"), closeSoftKeyboard())

        val materialButton2 = onView(
            Matchers.allOf(
                withId(R.id.post_track_button), withText("Crear"),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        5
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())
        onView(withText("Track agregado en album seleccionado"))
            .inRoot(ToastMatcher().apply {
                matches(isDisplayed())
            });
    }

    @Test
    fun create_album_empty() {
        val materialButton = onView(
            Matchers.allOf(
                withId(R.id.create_album_button), withText("Crear"),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val materialButton2 = onView(
            Matchers.allOf(
                withId(R.id.post_button), withText("Crear"),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        13
                    ),
                    2
                )
            )
        )
        materialButton2.perform(scrollTo(), click())
        onView(withText("Favor llenar todos los campos"))
            .inRoot(ToastMatcher().apply {
                matches(isDisplayed())
            });
    }

    @Test
    fun track_create_empty() {
               val albumnav = onView(withId(R.id.navigation_album)).check(matches(isDisplayed()))
            albumnav.perform(click())
            Thread.sleep(10000)
            onView(withId(R.id.albumsRv)).check(matches(isDisplayed()))

        val recyclerView = onView(
            Matchers.allOf(
                withId(R.id.albumsRv),
                childAtPosition(
                    withClassName(Matchers.`is`("android.widget.ScrollView")),
                    0
                )
            )
        )
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                2,
                click()
            )
        )
            Thread.sleep(2000)
        val materialButton = onView(
            Matchers.allOf(
                withId(R.id.create_track_button), withText("Crear Track"),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("android.widget.ScrollView")),
                        0
                    ),
                    2
                )
            )
        )

        materialButton.perform(scrollTo(), click())

        val materialButton2 = onView(
            Matchers.allOf(
                withId(R.id.post_track_button), withText("Crear"),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        5
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        onView(withText("Favor llenar todos los campos"))
            .inRoot(ToastMatcher().apply {
                matches(isDisplayed())
            });
    }

    @Test
    fun e2E() {
        val materialButton = onView(
            Matchers.allOf(
                withId(R.id.create_album_button), withText("Crear"),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val materialButton2 = onView(
            Matchers.allOf(
                withId(R.id.cancel_button), withText("Cancelar"),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        13
                    ),
                    0
                )
            )
        )
        materialButton2.perform(scrollTo(), click())

        val bottomNavigationItemView = onView(
            Matchers.allOf(
                withId(R.id.navigation_performer), withContentDescription("Artistas"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val bottomNavigationItemView2 = onView(
            Matchers.allOf(
                withId(R.id.navigation_collector), withContentDescription("Coleccionistas"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(click())

        val recyclerView = onView(
            Matchers.allOf(
                withId(R.id.collectorsRv),
                childAtPosition(
                    withClassName(Matchers.`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        val bottomNavigationItemView3 = onView(
            Matchers.allOf(
                withId(R.id.navigation_album), withContentDescription("Albumes"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView3.perform(click())

        val bottomNavigationItemView4 = onView(
            Matchers.allOf(
                withId(R.id.navigation_collector), withContentDescription("Coleccionistas"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView4.perform(click())

        val appCompatImageButton = onView(
            Matchers.allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    Matchers.allOf(
                        withId(androidx.constraintlayout.widget.R.id.action_bar),
                        childAtPosition(
                            withId(androidx.constraintlayout.widget.R.id.action_bar_container),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val bottomNavigationItemView5 = onView(
            Matchers.allOf(
                withId(R.id.navigation_album), withContentDescription("Albumes"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView5.perform(click())

        val recyclerView2 = onView(
            Matchers.allOf(
                withId(R.id.albumsRv),
                childAtPosition(
                    withClassName(Matchers.`is`("android.widget.ScrollView")),
                    0
                )
            )
        )
        recyclerView2.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                2,
                click()
            )
        )

        val bottomNavigationItemView6 = onView(
            Matchers.allOf(
                withId(R.id.navigation_performer), withContentDescription("Artistas"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView6.perform(click())

        val bottomNavigationItemView7 = onView(
            Matchers.allOf(
                withId(R.id.navigation_album), withContentDescription("Albumes"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView7.perform(click())

        val materialButton3 = onView(
            Matchers.allOf(
                withId(R.id.create_track_button), withText("Crear Track"),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("android.widget.ScrollView")),
                        0
                    ),
                    2
                )
            )
        )
        materialButton3.perform(scrollTo(), click())

        val textInputEditText = onView(
            Matchers.allOf(
                withId(R.id.txt_track_name),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("Honor"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            Matchers.allOf(
                withId(R.id.txt_track_duration),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("com.google.android.material.textfield.TextInputLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("3:15"), closeSoftKeyboard())

        val materialButton4 = onView(
            Matchers.allOf(
                withId(R.id.post_track_button), withText("Crear"),
                childAtPosition(
                    childAtPosition(
                        withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        5
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton4.perform(click())

        val recyclerView3 = onView(
            Matchers.allOf(
                withId(R.id.albumsRv),
                childAtPosition(
                    withClassName(Matchers.`is`("android.widget.ScrollView")),
                    0
                )
            )
        )
        recyclerView3.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                2,
                click()
            )
        )

        val bottomNavigationItemView8 = onView(
            Matchers.allOf(
                withId(R.id.navigation_performer), withContentDescription("Artistas"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView8.perform(click())

        val bottomNavigationItemView9 = onView(
            Matchers.allOf(
                withId(R.id.navigation_album), withContentDescription("Albumes"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView9.perform(click())

        val appCompatImageButton2 = onView(
            Matchers.allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    Matchers.allOf(
                        withId(androidx.constraintlayout.widget.R.id.action_bar),
                        childAtPosition(
                            withId(androidx.constraintlayout.widget.R.id.action_bar_container),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton2.perform(click())
    }


}
