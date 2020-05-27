package com.techm.optustest.photo

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.techm.optustest.R
import com.techm.optustest.ui.MainActivity
import com.techm.optustest.ui.album.AlbumViewHolder
import com.techm.optustest.ui.userinfo.UserViewHolder
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**UI test cases for photo fragment fragment**/
class FragmentPhotoTest {

    @get: Rule
    val activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, false)

    private val listItemPosition = 2

    @Before
    fun setUp() {
        val intent = Intent()
        activityRule.launchActivity(intent)
    }


    /**test photo frag,ent displayed**/
    @Test
    fun photoFragmentDisplaySuccess() {
        onView(withId(R.id.recyclerViewUser))
            .perform(RecyclerViewActions.actionOnItemAtPosition<UserViewHolder>(listItemPosition,ViewActions.click()))

        onView(withId(R.id.albumRecyclerView)).perform(RecyclerViewActions
            .actionOnItemAtPosition<AlbumViewHolder>(listItemPosition,ViewActions.click())
            )

        IdlingResource.ResourceCallback {
            onView(withText(R.id.albumDetailTitle))
                .check(ViewAssertions.matches(isDisplayed()))
        }
    }
}