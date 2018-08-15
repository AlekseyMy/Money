package yandexschool.dmpolyakov.money

import junit.framework.Assert.assertEquals
import org.junit.Test
import yandexschool.dmpolyakov.money.utils.daysToMillis
import yandexschool.dmpolyakov.money.utils.millsToDays
import yandexschool.dmpolyakov.money.utils.millsToSeconds
import yandexschool.dmpolyakov.money.utils.secondsToMills

class TimeUnitTest {

    @Test
    fun testDaysToMils_millsToDays_success() {
        assertEquals("1".daysToMillis(), 1L * 24L * 60L * 60L * 1000L)
        assertEquals((1L * 24L * 60L  * 60L * 1000L).millsToDays(), 1L)
    }

    @Test
    fun testSecondsToMils_millsToSeconds_success() {
        assertEquals("1".secondsToMills(), 1L * 1000L)
        assertEquals((1L * 1000L).millsToSeconds(), 1L)
    }
}