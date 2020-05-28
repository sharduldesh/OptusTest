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
class FragmentPhotoTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    /*
     * test clicks on first item from userinfo fragment and loads album fragment
     * clicks on first item and loads the photo fragment and checks
     * if all the content of pgoto fragment is displayed
     * */
    @Test
    fun testPhotoFragmentContentIsDisplayed() {
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
        Thread.sleep(7000)

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
        imageViewAlbumPhoto.perform(click())
        Thread.sleep(3000)

        val textViewAlbumDetailID = onView(
            allOf(
                withId(R.id.textViewAlbumDetailID), withText("Album ID : 1"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolBarHeaderView),
                        childAtPosition(
                            withId(R.id.mToolbarAlbumDetails),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textViewAlbumDetailID.check(matches(withText("Album ID : 1")))

        val textViewPhotoDetailID = onView(
            allOf(
                withId(R.id.textViewPhotoDetailID), withText("Photo ID : 1"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolBarHeaderView),
                        childAtPosition(
                            withId(R.id.mToolbarAlbumDetails),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textViewPhotoDetailID.check(matches(withText("Photo ID : 1")))

        val imageViewAlbumImage = onView(
            allOf(
                withId(R.id.imageViewAlbumImage), withContentDescription("album_details"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.scrollView),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        imageViewAlbumImage.check(matches(isDisplayed()))

        val albumDetailTitle = onView(
            allOf(
                withId(R.id.albumDetailTitle),
                withText("accusamus beatae ad facilis cum similique qui sunt"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        albumDetailTitle.check(matches(withText("accusamus beatae ad facilis cum similique qui sunt")))
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
