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

/**UI test cases for album fragment**/
class FragmentAlbumTest {

    @get: Rule
    val activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, false)

    private val scrollToPosition = 5

    @Before
    fun setUp() {
        val intent = Intent()
        activityRule.launchActivity(intent)
    }


    /**test album fragment is displayed**/
    @Test
    fun albumFragmentDisplaySuccess() {
        onView(withId(R.id.recyclerViewUser))
            .perform(RecyclerViewActions.actionOnItemAtPosition<UserViewHolder>(scrollToPosition,ViewActions.click()))

        onView(withId(R.id.albumRecyclerView))
            .check(matches(isDisplayed()))
    }

    /**test album fragment progress bar displayed**/
    @Test
    fun testFragmentAlbumProgressBarIsDisplayed() {

        onView(withId(R.id.recyclerViewUser))
            .perform(RecyclerViewActions.actionOnItemAtPosition<UserViewHolder>(scrollToPosition, ViewActions.click()))

        IdlingResource.ResourceCallback {onView(withId(R.id.progressBarUser)).check(matches(isDisplayed()))
        }
    }


    /**test scroll functionality on album list**/
    @Test
    fun albumRecyclerViewScrollToPosition() {
        onView(withId(R.id.recyclerViewUser))
            .perform(RecyclerViewActions.actionOnItemAtPosition<UserViewHolder>(scrollToPosition,ViewActions.click()))

        onView(withId(R.id.albumRecyclerView))
            .perform(RecyclerViewActions.scrollToPosition<UserViewHolder>(scrollToPosition))
    }

    /**test click functionality on item from album list**/
    @Test
    fun albumRecyclerviewOnClickItem() {
        onView(withId(R.id.recyclerViewUser))
            .perform(RecyclerViewActions.actionOnItemAtPosition<UserViewHolder>(scrollToPosition,ViewActions.click()))

        onView(withId(R.id.albumRecyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<AlbumViewHolder>(scrollToPosition,ViewActions.click()))
    }
}