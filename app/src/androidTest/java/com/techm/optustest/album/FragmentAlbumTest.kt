package com.techm.optustest.ui


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
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class FragmentAlbumTest  {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    /*
    * test click on first item from userinfo fragment and loads album fragment
    * checks if all the content of album list fragment is displayed
    * */

    @Test
    fun testAlbumListPageContentisDisplayed() {
        Thread.sleep(3000)
        val userCardView = onView(
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
        userCardView.perform(click())
        Thread.sleep(5000)
        val albumTitle = onView(
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
        albumTitle.check(matches(withText("Album ID: 1")))

        val imageViewAlbumPhoto = onView(
            allOf(
                firstView(withId(R.id.imageViewAlbumPhoto)),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.albumListCardView),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        imageViewAlbumPhoto.check(matches(isDisplayed()))


        val textViewAlbumTitle = onView(
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
        textViewAlbumTitle.check(matches(withText("accusamus beatae ad facilis cum similique qui sunt")))
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
    /**matcher to match first imageview present in list**/
    private fun <T> firstView(matcher: Matcher<T>): Matcher<T>? {
        return object : BaseMatcher<T>() {
            var isFirst = true
            override fun matches(item: Any): Boolean {
                if (isFirst && matcher.matches(item)) {
                    isFirst = false
                    return true
                }
                return false
            }

            override fun describeTo(description: Description) {
                description.appendText("should return first matching item")
            }
        }
    }
}
