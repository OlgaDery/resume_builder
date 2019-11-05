package com.google.resumemaker

import android.view.Gravity
import android.widget.DatePicker
import android.widget.LinearLayout
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
class EditProfileFragmentTest {

    private var activity: MainActivity? = null
    val firstName = "Aaaaaaaaa"
    val newFirstName = "111111111"

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
    fun fragmentEditProfile_containsAllEditTextItems() {
        onView(withId(R.id.edit_profile_wrapper_layout)).check(
            matches(allOf(isDisplayed(), instanceOf(LinearLayout::class.java)))
        )
        onView(withId(R.id.edit_profile_wrapper_layout)).check(
            matches(
                allOf(
                    hasDescendant(allOf(withId(R.id.first_name_edit_text), isEnabled(),
                        instanceOf(com.google.android.material.textfield.TextInputEditText::class.java)))
                    ,hasDescendant(allOf(withId(R.id.last_name_edit_text), isEnabled(),
                        instanceOf(com.google.android.material.textfield.TextInputEditText::class.java)))
                    ,hasDescendant(allOf(withId(R.id.email_edit_text), isEnabled(),
                        instanceOf(com.google.android.material.textfield.TextInputEditText::class.java)))
                    ,hasDescendant(allOf(withId(R.id.position_edit_text), isEnabled())))))

        onView(withId(R.id.dob_edit_text)).check(matches(not(isEnabled())))
    }

    @Test
    fun fragmentEditProfile_containsAllButtons() {
        onView(withId(R.id.edit_profile_wrapper_layout)).check(
            matches(
                allOf(
                    hasDescendant(allOf(withId(R.id.set_date_button), isEnabled(), isClickable()))
                    ,hasDescendant(allOf(withId(R.id.save_profile_button), isEnabled(), isClickable()))
                    ,hasDescendant(allOf(withId(R.id.cancel_edit_button), isEnabled(), isClickable()
                    )))))
    }

   @Test
    fun fragmentEditProfile_changeValuesAndCheckResult() {
        onView(withId(R.id.first_name_edit_text)).perform(ViewActions.clearText())
        onView(withId(R.id.first_name_edit_text)).perform(ViewActions.typeText(firstName))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.edit_profile_scroll_view)).perform(ViewActions.swipeUp())
        onView(withId(R.id.save_profile_button)).perform(click())
        onView(withId(R.id.first_name_text_view)).check(matches(withText(firstName)))
    }

   @Test
    fun fragmentEditProfile_cancelEditAndCheckResult() {
        onView(withId(R.id.first_name_edit_text)).perform(ViewActions.clearText())
        onView(withId(R.id.first_name_edit_text)).perform(ViewActions.typeText(newFirstName))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.edit_profile_scroll_view)).perform(ViewActions.swipeUp())
        onView(withId(R.id.cancel_edit_button)).perform(click())
        onView(withId(R.id.first_name_text_view)).check(matches(withText(firstName)))
    }

    @Test
    fun fragmentEditProfile_changeDate_check(){
        onView(withId(R.id.set_date_button)).perform(click())
        onView(instanceOf(DatePicker::class.java)).check(matches(isDisplayed()))
        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name))).perform(
            PickerActions.setDate(2018, 5, 6
            )
        )
        onView(withId(android.R.id.button1)).perform(click())
        onView(withId(R.id.dob_edit_text)).check(matches(withText("05/06/18")))
    }

}
