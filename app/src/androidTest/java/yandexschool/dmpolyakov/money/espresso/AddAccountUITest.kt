package yandexschool.dmpolyakov.money.espresso

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.RootMatchers.isDialog
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.delegateadapter.delegate.KDelegateAdapter
import kotlinx.android.synthetic.main.fragment_tracker.*
import org.hamcrest.Matchers.*
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import yandexschool.dmpolyakov.money.R
import yandexschool.dmpolyakov.money.ui.MainActivity
import yandexschool.dmpolyakov.money.ui.tracker.TrackerFragment


@RunWith(AndroidJUnit4::class)
class AddAccountUITest {

    @get:Rule
    val activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    companion object {

        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            InstrumentationRegistry.getTargetContext().deleteDatabase("Money")
        }
    }

    @Test
    fun testFragment() {
        onView(allOf(withId(R.id.fabAddAccount), isDisplayed())).perform(click())
        onView(allOf(withId(R.id.dialogTitle), isDisplayed())).inRoot(isDialog())
        onView(allOf(withId(R.id.inputTitle), isDisplayed()))
                .perform(typeText("testAccount"))
        onView(allOf(withId(R.id.inputAmount), isDisplayed()))
                .perform(typeText("0"))
        onView(allOf(withId(R.id.create), isDisplayed())).perform(click())

        val position = (activityTestRule.activity.supportFragmentManager
                .fragments.last() as TrackerFragment)
                .rvOperations.adapter?.itemCount!! - 1

        onView(allOf(withId(R.id.rvOperations), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition<KDelegateAdapter.KViewHolder>(position))

        onView(withText("testAccount")).check(matches(isDisplayed()))
    }
}