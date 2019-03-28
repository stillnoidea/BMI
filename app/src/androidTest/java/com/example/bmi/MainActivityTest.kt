package com.example.bmi


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTestCountBmi() {
        val inputWeight = onView(withId(R.id.main_text_weight_input))
        inputWeight.perform(scrollTo(), replaceText("56"), closeSoftKeyboard())

        val inputHeight = onView(withId(R.id.main_text_height_input))
        inputHeight.perform(scrollTo(), replaceText("160"), closeSoftKeyboard())

        val countBmi = onView(allOf(withId(R.id.main_button_count_bmi), withText("Count")))
        countBmi.perform(scrollTo(), click())

        val result = onView(withId(R.id.main_text_result_bmi))
        result.check(matches(withText("21.88")))
    }

    @Test
    fun mainVisibilityTest() {
        val weightText = onView(withId(R.id.main_text_weight_label))
        weightText.check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        val heightText = onView(withId(R.id.main_text_height_label))
        heightText.check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        val countButton = onView(withId(R.id.main_button_count_bmi))
        countButton.check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }





}
