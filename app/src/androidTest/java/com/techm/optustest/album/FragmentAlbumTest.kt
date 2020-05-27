package com.techm.optustest.album

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.techm.optustest.R
import com.techm.optustest.ui.MainActivity
import com.techm.optustest.ui.album.AlbumViewHolder
import com.techm.optustest.ui.userinfo.UserViewHolder
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FragmentAlbumTest {

    @get: Rule
    val activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, false)

    private val listItemPosition = 5

    @Before
    fun setUp() {
        val intent = Intent()
        activityRule.launchActivity(intent)
    }

    @Test
    fun testFragmentAlbumProgressBarIsDisplayed() {

        onView(withId(R.id.recyclerViewUser))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<UserViewHolder>(
                    listItemPosition,
                    ViewActions.click()
                )
            )

        IdlingResource.ResourceCallback {
            onView(withId(R.id.progressBarUser))
                .check(matches(isDisplayed()))
        }
    }


    @Test
    fun testRecyclerViewFragmentAlbumTestScrollingToPosition() {
        onView(withId(R.id.recyclerViewUser))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<UserViewHolder>(
                    listItemPosition,
                    ViewActions.click()
                )
            )
        onView(withId(R.id.albumRecyclerView))
            .perform(RecyclerViewActions.scrollToPosition<UserViewHolder>(1))
    }

    @Test
    fun testRecyclerviewAlbumOnClickItem() {
        onView(withId(R.id.recyclerViewUser))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<UserViewHolder>(
                    listItemPosition,
                    ViewActions.click()
                )
            )
        onView(withId(R.id.albumRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<AlbumViewHolder>(
                    0,
                    ViewActions.click()
                )
            )
    }
}