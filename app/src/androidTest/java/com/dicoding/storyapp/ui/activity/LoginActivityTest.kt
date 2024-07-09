package com.dicoding.storyapp.ui.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.dicoding.storyapp.R
import com.dicoding.storyapp.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityTest {
    @get:Rule
    val activity = ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun logout() {
        onView(withId(R.id.ed_login_email)).perform(click(), typeText("dicoding12345@gmail.com")).perform(closeSoftKeyboard())
        onView(withId(R.id.ed_login_password)).perform(click(), typeText("dicoding12345")).perform(closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())

        Thread.sleep(6000)

        onView(withId(R.id.app_bar_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.action_logout)).perform(click())
        onView(withText(R.string.yes)).perform(click())
    }

    @Test
    fun login() {
        onView(withId(R.id.ed_login_email)).perform(click(), typeText("dicoding12345@gmail.com")).perform(closeSoftKeyboard())
        onView(withId(R.id.ed_login_password)).perform(click(), typeText("dicoding12345")).perform(closeSoftKeyboard())
        onView(withId(R.id.btn_login)).perform(click())

        Thread.sleep(6000)

        onView(withId(R.id.app_bar_layout)).check(matches(isDisplayed()))
    }
}