package com.example.planner.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planner.data.database.Task
import com.example.planner.data.database.TaskRepository
import com.example.planner.util.getFormattedCurrentTime
import com.example.planner.util.importantObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
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



    private val _uiState = MutableStateFlow(TaskUiState(getTasksByDate(LocalDate.now())))
      val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()



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
        _uiState.update { currentState -> currentState.copy(taskList = getTasks(currentState.filterMap.getOrPut(selectedDate){TaskType.All},selectedDate), selectedDate = selectedDate) }
   }

      fun getTasksByDate(date:LocalDate): Flow<List<Task>> =
        taskRepository.getTasksByDate(date.toString())

    fun onFilterSelected(taskType: TaskType){
        _uiState.value.filterMap[_uiState.value.selectedDate]=taskType
        _uiState.update { currentState -> currentState.copy(taskList = getTasks(taskType, _uiState.value.selectedDate)) }

        System.out.println(_uiState.value.taskList.toString()+"shiva***")

    }
     private fun getTasks(taskType: TaskType, date: LocalDate): Flow<List<Task>> =
          taskRepository.getTasksByTypeAndDate(taskType, date.toString())


}