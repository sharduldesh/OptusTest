package com.techm.optustest.ui

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.techm.optustest.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/** @class Ui testing class to test user information screen (screen 1). **/

@LargeTest
@RunWith(AndroidJUnit4::class)
class FragmentUserTest {

    @get: Rule
    val activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, false)

    private val intent = Intent()
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        activityRule.launchActivity(intent)
    }

    /**test app launching success**/
    @Test
    fun appLaunchSuccess() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    /**test title bar is displayed**/
    @Test
    fun titleBarDisplay() {

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



    /**test to check item click functionality**/
    @Test
    fun performcardFirstItemclick() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        onView(withId(R.id.recyclerViewUser)).check(ViewAssertions.matches(isDisplayed()));

        onView(withId(R.id.recyclerViewUser)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

    }


    /**test for checking scrolling functionality**/
    @Test
    fun listScrollToLastAndFirst() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        val recyclerview: RecyclerView =
            mActivityTestRule.activity.findViewById<RecyclerView>(R.id.recyclerViewUser)
        val count = recyclerview.adapter?.itemCount
        onView(withId(R.id.recyclerViewUser)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                count!!.toInt()
            )
        )

        onView(withId(R.id.recyclerViewUser)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                0
            )
        )
    }

    /**test to check all the content of userifo(ID,Name,Phone no.,Email) fragment is displayed**/
    @Test
    fun testUserinfoPageContentisDisplayed() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        val textViewUserID = onView(
            allOf(
                withId(R.id.textViewUserID), withText("ID : 1"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.userCardView),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textViewUserID.check(matches(withText("ID : 1")))

        val textViewUserName = onView(
            allOf(
                withId(R.id.textViewUserName), withText("Name : Leanne Graham"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.userCardView),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textViewUserName.check(matches(withText("Name : Leanne Graham")))

        val textViewUserEmail = onView(
            allOf(
                withId(R.id.textViewUserEmail), withText("Email : Sincere@april.biz"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.userCardView),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        textViewUserEmail.check(matches(withText("Email : Sincere@april.biz")))

        val textViewUserPhone = onView(
            allOf(
                withId(R.id.textViewUserPhone), withText("Phone : 1-770-736-8031 x56442"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.userCardView),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        textViewUserPhone.check(matches(withText("Phone : 1-770-736-8031 x56442")))
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
}
