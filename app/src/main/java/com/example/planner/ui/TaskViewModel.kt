package com.example.planner.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planner.data.database.Task
import com.example.planner.data.database.TaskRepository
import com.example.planner.util.getFormattedCurrentTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {



    private val _uiState = TaskUiState(getTasksByDate(LocalDate.now()),mutableStateOf(LocalDate.now()))
      val uiState: TaskUiState = _uiState

   init {
      // setUiState(LocalDate.now().toString())
   }

   /* private fun setUiState(selectedDate: String) {
         viewModelScope.launch{
                 _uiState.copy(
                     selectedDate = mutableStateOf(
                         selectedDate
                     ), taskList = getTasksByDate(selectedDate)
                 )

           *//* _uiState.update { currentState ->
                currentState.copy(
                    selectedDate = mutableStateOf(
                        selectedDate
                    ), taskList = getTasksByDate(selectedDate)
                )
            }*//*
        }
    }
*/
   /* private fun getUiState(selectedDate: String): TaskUiState {

            return TaskUiState(
                selectedDate = mutableStateOf(selectedDate),
                taskList = getTasksByDate(selectedDate)
            )

    }*/

    fun addTask(title:String, date:LocalDate) {
        // add to db and update list
        viewModelScope.launch(IO) {
            //task.time= getFormattedCurrentTime()
            taskRepository.addTask(Task( title=title, date= date.toString(), time = getFormattedCurrentTime()))
        }

    }

    fun addTask(task: Task) {
        // add to db and update list
        viewModelScope.launch(IO) {
            //task.time= getFormattedCurrentTime()
            taskRepository.addTask(Task( title=task.title, date= task.date, time = getFormattedCurrentTime()))
       }

    }

    fun deleteTask(task: Task) {
        //delete from db and update list
      viewModelScope.launch(IO) {
            taskRepository.deleteTask(task)
        }

    }

    fun updateTask(task: Task) {
        //update in db and update list
      viewModelScope.launch(IO) {
          taskRepository.updateTask(task)

      }
    }

    fun onDateSelected(selectedDate:LocalDate){
        _uiState.selectedDate.value=selectedDate
        _uiState.taskList=getTasksByDate(selectedDate)
    }

     private fun getTasksByDate(date:LocalDate): Flow<List<Task>> =
        taskRepository.getTasksByDate(date.toString())

}