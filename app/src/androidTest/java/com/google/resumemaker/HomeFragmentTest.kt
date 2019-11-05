package com.google.resumemaker

import android.view.Gravity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Before
import org.junit.Rule
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import org.hamcrest.Matchers


@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    private var activity: MainActivity? = null
    val firstName = "Aaaaaaaaa"

    @get: Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java,
        true, true)

    @Before
    fun setUp() {
        activity = activityRule.activity
        onView(withId(R.id.drawer_layout)).check(matches(allOf(isEnabled())))
        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_profile))
        onView(withId(R.id.edit_profile_button)).perform(click())
    }


    @Test
    fun fragmentEditProfile_changeValuesAndCheckResult() {
        onView(withId(R.id.first_name_edit_text)).perform(ViewActions.clearText())
        onView(withId(R.id.first_name_edit_text)).perform(ViewActions.typeText(firstName))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.edit_profile_scroll_view)).perform(ViewActions.swipeUp())
        onView(withId(R.id.save_profile_button)).perform(click())
        onView(withId(R.id.first_name_text_view)).check(matches(withText(firstName)))

        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_home))
        onView(withId(R.id.full_resume_text_view)).check(matches(isDisplayed()))
        onView(withId(R.id.header)).check(matches(allOf(isDisplayed(), withText(activity!!.getString(R.string.overview_header)))))
        //TODO test share_button
        onView(withId(R.id.no_resume_message)).check(matches(not(isDisplayed())))
    }

}
