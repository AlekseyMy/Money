package yandexschool.dmpolyakov.money.espresso

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import yandexschool.dmpolyakov.money.R
import yandexschool.dmpolyakov.money.ui.MainActivity

@RunWith(AndroidJUnit4::class)
class AddFinanceOperationUITest {

    @get:Rule
    val activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun openAccountFragment() {
        onView(allOf(withText("testAccount"), isDisplayed())).perform(click())
        onView(allOf(withId(R.id.fabAddOperation), isDisplayed())).perform(click())
        onView(allOf(withId(R.id.inputTitle), isDisplayed()))
                .perform(typeText("testFinanceOperation"))
        onView(allOf(withId(R.id.inputAmount), isDisplayed()))
                .perform(typeText("100"), closeSoftKeyboard())
        onView(allOf(withId(R.id.add), isDisplayed()))
                .perform(click())

        onView(allOf(withId(R.id.balance), isDisplayed())).check(matches(withText("100.00 \u20BD")))
    }
}