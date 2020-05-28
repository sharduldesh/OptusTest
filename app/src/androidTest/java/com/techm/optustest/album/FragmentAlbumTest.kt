package com.techm.optustest.album


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.techm.optustest.R
import com.techm.optustest.ui.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/** @class Ui testing class to test album screen(screen 2). **/

@LargeTest
@RunWith(AndroidJUnit4::class)
class FragmentAlbumTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    /*
     * test click on first item from userinfo fragment and loads album fragment
     * checks if all the content of album list fragment is displayed
     * */

    @Test
    fun testAlbumListPageContentisDisplayed() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        val cardView = onView(
            allOf(
                withId(R.id.userCardView),
                childAtPosition(
                    allOf(
                        withId(R.id.recyclerViewUser),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            2
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        cardView.perform(click())
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        val textView = onView(
            allOf(
                withId(R.id.albumTitle), withText("Album ID: 1"),
                childAtPosition(
                    allOf(
                        withId(R.id.albumToolbar),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Album ID: 1")))

        val textView2 = onView(
            allOf(
                withId(R.id.textViewAlbumTitle),
                withText("accusamus beatae ad facilis cum similique qui sunt"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.albumListCardView),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("accusamus beatae ad facilis cum similique qui sunt")))
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
