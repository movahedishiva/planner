package com.shiva.planner.util

import com.shiva.planner.data.database.Task
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`

import org.junit.Test
import java.time.LocalDate

class TaskUtilTest {

    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsHundredZero() {


            val tasks= listOf<Task>(Task(id=1,title="test",date= getFormattedDate(LocalDate.now()),time= getFormattedCurrentTime(), isCompleted = false))
            val result=getActivatedAndCompletedTaskState(tasks)
             assertThat(result.activeTaskPercent, `is`(100f))
             assertThat(result.completedTaskPercent, `is`(0f))

        }

    @Test
    fun getActiveAndCompletedStats_emptyList_returnZeros(){

        val tasks= emptyList<Task>()
        val result=getActivatedAndCompletedTaskState(tasks)
        assertThat(result.activeTaskPercent, `is`(0f))
        assertThat(result.completedTaskPercent, `is`(0f))

    }

    @Test
    fun getActiveAndCompletedStats_noActive_returnsZeroHundred() {


        val tasks= listOf<Task>(Task(id=1,title="test",date= getFormattedDate(LocalDate.now()),time= getFormattedCurrentTime(), isCompleted = true))
        val result=getActivatedAndCompletedTaskState(tasks)
        assertThat(result.activeTaskPercent, `is`(0f))
        assertThat(result.completedTaskPercent, `is`(100f))

    }

    @Test
    fun getActiveAndCompletedStats_error_returnsZeros() {


        val tasks= null
        val result=getActivatedAndCompletedTaskState(tasks)
        assertThat(result.activeTaskPercent, `is`(0f))
        assertThat(result.completedTaskPercent, `is`(0f))

    }

    @Test
    fun getActiveAndCompletedStats_both_returnsFortySixty() {


        val tasks= listOf(
         Task(id=1,title="test",date= getFormattedDate(LocalDate.now()),time= getFormattedCurrentTime(), isCompleted = false)
        ,Task(id=2,title="test",date= getFormattedDate(LocalDate.now()),time= getFormattedCurrentTime(), isCompleted = false)
        ,Task(id=3,title="test",date= getFormattedDate(LocalDate.now()),time= getFormattedCurrentTime(), isCompleted = true)
        ,Task(id=4,title="test",date= getFormattedDate(LocalDate.now()),time= getFormattedCurrentTime(), isCompleted = true)
        ,Task(id=5,title="test",date= getFormattedDate(LocalDate.now()),time= getFormattedCurrentTime(), isCompleted = true))
        val result=getActivatedAndCompletedTaskState(tasks)
        assertThat(result.activeTaskPercent, `is`(40f))
        assertThat(result.completedTaskPercent, `is`(60f))

    }

}