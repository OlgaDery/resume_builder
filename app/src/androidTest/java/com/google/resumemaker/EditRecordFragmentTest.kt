package com.google.resumemaker

import android.view.Gravity
import android.widget.DatePicker
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class EditRecordFragmentTest {

    private var activity: MainActivity? = null
    val achievementsValue = "1111111"
    val companyValue = "Comp"
    val descrValue = "Descript"
    val headerValue = UUID.randomUUID().toString()

    @get: Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java, true,
        true)

    @Before
    fun setUp() {
        activity = activityRule.activity
        onView(withId(R.id.drawer_layout))
            .check(matches(allOf(isEnabled())))
        onView(withId(R.id.drawer_layout))
            .check(matches(DrawerMatchers.isClosed(Gravity.LEFT)))
            .perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_education))
        onView(withId(R.id.add_record_button)).perform(ViewActions.click())
    }

    @Test
    fun fragmentEditRecord_allEditViewsDisplayed(){
       checkIfEditRecordElementsVisible()
        Thread.sleep(300)
    }

   @Test
    fun fragmentEditRecord_createNewRecord_checkResult(){
        onView(withId(R.id.achievement_edit_text)).perform(ViewActions.replaceText(achievementsValue))
        onView(withId(R.id.title_edit_text)).perform(ViewActions.replaceText(headerValue))
        onView(withId(R.id.descr_edit_text)).perform(ViewActions.replaceText(descrValue))
        onView(withId(R.id.company_edit_text)).perform(ViewActions.replaceText(companyValue))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.scroll_modify_record)).perform(ViewActions.swipeUp())

        onView(withId(R.id.set_start_button)).perform(ViewActions.click())
        onView(instanceOf(DatePicker::class.java)).check(matches(isDisplayed()))
        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name))).perform(
            PickerActions.setDate(2018, 5, 6
            )
        )
        onView(withId(android.R.id.button1)).perform(ViewActions.click())

        onView(withId(R.id.save_record_button)).perform(ViewActions.click())

        onView(withId(R.id.records_recycler_view)).perform(scrollToPosition<RecyclerView.ViewHolder>(0))
            .check(matches(
                hasDescendant(allOf(withId(R.id.recycler_view_item_card_view), isClickable(), instanceOf(CardView::class.java)
                    )))).perform(ViewActions.click())

        checkIfViewRecordElementsVisible()
        onView(withId(R.id.view_record_scroll_view)).perform(ViewActions.swipeUp())
        onView(withId(R.id.edit_record_button)).perform(ViewActions.click())
        checkIfEditRecordElementsVisible()
    }

    fun checkIfViewRecordElementsVisible() {
        onView(withId(R.id.view_record_card_view)).check(
            matches(
                allOf(
                    hasDescendant(allOf(withId(R.id.achievements_text_view), isDisplayed()))
                    ,hasDescendant(allOf(withId(R.id.title_text_view), isDisplayed()))
                    ,hasDescendant(allOf(withId(R.id.descript_text_view), isDisplayed()))
                )))

        onView(withId(R.id.view_record_layout)).check(
            matches(
                allOf(
                    hasDescendant(allOf(withId(R.id.edit_record_button), isClickable()))
                    ,hasDescendant(allOf(withId(R.id.delete_record_button), isClickable()))
                )))
    }

    fun checkIfEditRecordElementsVisible() {

        onView(withId(R.id.edit_record_wrapper_layout)).check(
            matches(
                allOf(
                    hasDescendant(allOf(withId(R.id.achievement_edit_text), isEnabled(),
                        instanceOf(com.google.android.material.textfield.TextInputEditText::class.java)))
                    ,hasDescendant(allOf(withId(R.id.title_edit_text), isEnabled(),
                        instanceOf(com.google.android.material.textfield.TextInputEditText::class.java)))
                    ,hasDescendant(allOf(withId(R.id.descr_edit_text), isEnabled(),
                        instanceOf(com.google.android.material.textfield.TextInputEditText::class.java)))
                    ,hasDescendant(allOf(withId(R.id.company_edit_text), isEnabled(),
                        instanceOf(com.google.android.material.textfield.TextInputEditText::class.java)))
                    ,hasDescendant(allOf(withId(R.id.location_edit_text), isEnabled(),
                        instanceOf(com.google.android.material.textfield.TextInputEditText::class.java)))
                    ,hasDescendant(allOf(withId(R.id.faculty_edit_text), isEnabled(),
                        instanceOf(com.google.android.material.textfield.TextInputEditText::class.java))))))


        onView(withId(R.id.edit_record_wrapper_layout)).check(
            matches(
                allOf(
                    hasDescendant(allOf(withId(R.id.save_record_button), isEnabled(),
                        isClickable()))
                    ,hasDescendant(allOf(withId(R.id.cancel_edit_record_button), isEnabled(),
                        isClickable()))
                    ,hasDescendant(allOf(withId(R.id.set_end_button), isEnabled(),
                        isClickable()))
                    ,hasDescendant(allOf(withId(R.id.set_start_button), isEnabled(), isClickable()))
                )))

    }
}