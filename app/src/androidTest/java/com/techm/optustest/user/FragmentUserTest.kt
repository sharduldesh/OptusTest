package com.techm.optustest.user

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.techm.optustest.R
import com.techm.optustest.ui.MainActivity
import com.techm.optustest.ui.userinfo.FragmentUser
import com.techm.optustest.ui.userinfo.UserViewHolder
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class FragmentUserTest {

    @get: Rule
    val activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, false)

    private val scrollToPosition = 3
    private val intent = Intent()

    @Before
    fun setUp() {
        activityRule.launchActivity(intent)
    }

    @Test
    fun appLaunchSuccess() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun userFragmentDisplaySuccess() {
        val fragmentUser = FragmentUser()
        activityRule.activity.supportFragmentManager.beginTransaction()
            .add(R.id.base_fragment, fragmentUser).commit()
    }

    @Test
    fun progressBarDisplay() {

        val textView = onView(
            Matchers.allOf(
                withId(R.id.mToolbarUserTitle), ViewMatchers.withText("User Info"),
                childAtPosition(
                    Matchers.allOf(
                        withId(R.id.mToolbarUser),
                        childAtPosition(IsInstanceOf.instanceOf(ViewGroup::class.java), 0)
                    ), 0
                ), isDisplayed()
            )
        )
        textView.check(matches(withText("User Info")))
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
    fun onLaunchCheckProgressBarIsDisplayed() {
        IdlingResource.ResourceCallback {
            onView(withId(R.id.progressBarUser))
                .check(matches(isDisplayed()))
        }
    }

    @Test
    fun testRecyclerViewFragmentUserTestScrollToPosition() {
        onView(withId(R.id.recyclerViewUser))
            .perform(RecyclerViewActions.scrollToPosition<UserViewHolder>(scrollToPosition))
    }

    @Test
    fun testRecyclerviewUserOnClickItem() {
        onView(withId(R.id.recyclerViewUser))
            .perform(actionOnItemAtPosition<UserViewHolder>(scrollToPosition, click()))
    }

    @Test
    fun recyclerViewFragmentUserIsDisplayed() {
        onView(withId(R.id.recyclerViewUser)).check(matches(isDisplayed()))
    }
    
     @Test
    fun performcardFirstItemclick() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        onView(withId(R.id.recyclerViewUser)).check(ViewAssertions.matches(isDisplayed()));

        onView(withId(R.id.recyclerViewUser)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()))

    }
    @Test
    fun listScrollToEnd(){
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        val recyclerview:RecyclerView=mActivityTestRule.activity.findViewById<RecyclerView>(R.id.recyclerViewUser)
        val count=recyclerview.adapter?.itemCount
        onView(withId(R.id.recyclerViewUser)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(count!!.toInt()))

    }
}
