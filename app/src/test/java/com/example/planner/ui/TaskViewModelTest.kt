package com.example.planner.ui


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.planner.PlannerApplication
import com.example.planner.data.database.Task
import com.example.planner.util.getFormattedCurrentTime
import com.example.planner.util.getFormattedDate
import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class TaskViewModelTest {


    @Test
    fun addNewTask_setsNewTaskEvent() {

        val context: Context = ApplicationProvider.getApplicationContext()
       // val viewmodel = TaskViewModel(PlannerApplication().container.taskRepository)
       // viewmodel.addTask(Task(id=1,title="test",date= getFormattedDate(LocalDate.now()),time= getFormattedCurrentTime(), isCompleted = false))
    }
}