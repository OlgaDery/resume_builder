package com.google.resumemaker

import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileFragmentTest {

    private var activity: MainActivity? = null

    @get: Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java, true,
        true)

    @Before
    fun setUp() {
        activity = activityRule.activity
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
            .check(ViewAssertions.matches(CoreMatchers.allOf(ViewMatchers.isEnabled())))
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
            .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())
        Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_profile))
    }

    @Test
    fun fragmentProfile_containsAllItems() {
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
            .check(ViewAssertions.matches(CoreMatchers.allOf(ViewMatchers.isEnabled()))) //hasBackground(R.drawable.ic_profile)
//
        Espresso.onView(ViewMatchers.withId(R.id.profile_card_view)).check(
            ViewAssertions.matches(
                CoreMatchers.allOf(
                    ViewMatchers.isDisplayed(), CoreMatchers.instanceOf(
                        CardView::class.java
                    ), ViewMatchers.isDescendantOfA(ViewMatchers.withId(R.id.profile_scroll_view))
                )
            )
        )

        Espresso.onView(ViewMatchers.withId(R.id.first_name_layout)).check(
            ViewAssertions.matches(
                CoreMatchers.allOf(
                    ViewMatchers.isDisplayed(),
                    CoreMatchers.instanceOf(LinearLayout::class.java),
                    ViewMatchers.isDescendantOfA(ViewMatchers.withId(R.id.profile_scroll_view))
                )
            )
        )

        Espresso.onView(ViewMatchers.withId(R.id.first_name_text_view)).check(
            ViewAssertions.matches(
                CoreMatchers.allOf(
                    ViewMatchers.isDisplayed(),
                    CoreMatchers.instanceOf(TextView::class.java),
                    ViewMatchers.hasSibling(ViewMatchers.withId(R.id.first_name_header))
                )
            )
        )

        Espresso.onView(ViewMatchers.withId(R.id.last_name_text_view)).check(
            ViewAssertions.matches(
                CoreMatchers.allOf(
                    ViewMatchers.isDisplayed(),
                    CoreMatchers.instanceOf(TextView::class.java),
                    ViewMatchers.hasSibling(ViewMatchers.withText(activity!!.getString(R.string.last_name)))
                )
            )
        )

        Espresso.onView(ViewMatchers.withId(R.id.edit_profile_button)).check(
            ViewAssertions.matches(
                CoreMatchers.allOf(
                    ViewMatchers.isDisplayed(),
                    CoreMatchers.instanceOf(FloatingActionButton::class.java),
                    ViewMatchers.isClickable()
                )
            )
        )
    }
}