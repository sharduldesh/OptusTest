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

/**UI test cases for userinfo fragment**/
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

    /**test app launch success**/
    @Test
    fun appLaunchSuccess() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    /**test userinfo fragment is displayed**/
    @Test
    fun userFragmentDisplaySuccess() {
        val fragmentUser = FragmentUser()
        activityRule.activity.supportFragmentManager.beginTransaction()
            .add(R.id.base_fragment, fragmentUser).commit()
    }

    /**test userinfo fragment progress bar displayed**/
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

    /**test scroll functionality on user list**/
    @Test
    fun testRecyclerViewScrollToPosition() {
        onView(withId(R.id.recyclerViewUser))
            .perform(RecyclerViewActions.scrollToPosition<UserViewHolder>(scrollToPosition))
    }

    /**test click functionality on item from userinfo list**/
    @Test
    fun testRecyclerviewOnClickItem() {
        onView(withId(R.id.recyclerViewUser))
            .perform(actionOnItemAtPosition<UserViewHolder>(scrollToPosition, click()))
    }

}