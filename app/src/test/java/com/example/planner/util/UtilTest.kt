package com.example.planner.util

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime

class UtilTest {

    @Test
    fun getFormattedTime_returnFormattedDate(){
        assertThat("22:06",`is`( getFormattedTime(LocalTime.parse("22:06:31.061"))))

    }

    @Test
    fun getFormattedDate_return22October2024(){
        assertThat("22 October 2024", `is`(getFormattedDate(LocalDate.parse("2024-10-22"))))
    }
}