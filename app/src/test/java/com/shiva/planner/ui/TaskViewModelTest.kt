package com.shiva.planner.ui

import com.shiva.planner.data.database.Task
import com.shiva.planner.data.database.TaskRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class TaskViewModelTest{

   // @get:Rule
   // val mainDispatcherRule = MainDispatcherRule()
/*

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun ViewModel_addFirstTask_ListSizeIsOne_AllType() =runTest{

        val mockRepo:TaskRepository = mockk(relaxed = true)
        val viewModel=TaskViewModel(mockRepo)
        val uiState=viewModel.uiState.value
        val task=Task(title = "test1", date = uiState.selectedDate.toString())

        viewModel.addTask(task)
      //   advanceUntilIdle()
      //  coVerify { mockRepo.addTask(task) }

        //assertEquals(uiState.taskList.first()[0],task)

        coVerify(exactly = 1) {
            mockRepo.addTask(match {
                it.title == "test1" &&
                        it.date == task.date &&
                        it.time.isNotBlank()
            })
        }
    }
*/

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `addTask should call repository addTask with formatted time`() = runTest {
        // Arrange
        val mockRepo = mockk<TaskRepository>(relaxed = true)
        val viewModel = TaskViewModel(mockRepo)

        val task = Task(
            title = "Test Task",
            date = LocalDate.now().toString(),
            time = "" // time will be set by getFormattedCurrentTime
        )

        // Act
        viewModel.addTask(task)
        advanceUntilIdle()

        // Assert
        coVerify(exactly = 1) {
            mockRepo.addTask(match {
                it.title == "Test Task" &&
                        it.date == task.date &&
                        it.time.isNotBlank()
            })
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getTasksByDate()= runTest{
        val mockRepo = mockk<TaskRepository>(relaxed = true)
        val viewModel = TaskViewModel(mockRepo)

        val localDate=LocalDate.now()

        val task = Task(
            title = "Test Task",
            date = LocalDate.now().toString(),
            time = "" // time will be set by getFormattedCurrentTime
        )

        // Define mock behavior
        coEvery{ mockRepo.getTasksByDate(localDate.toString()) } returns flowOf(listOf(task) )

        // Call ViewModel method
        val result=viewModel.getTasksByDate(localDate)

        // Ensures all coroutine tasks are completed
        // before proceeding with assertions.
        advanceUntilIdle()

        // Verify interactions
        coVerify { mockRepo.getTasksByDate(localDate.toString()) }

        println("result:" + result.first())

        // Output: John Doe
        assertEquals(flowOf(listOf(task)).first(), result.first())


    }


}