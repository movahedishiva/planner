package com.example.planner.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planner.data.database.Task
import com.example.planner.data.database.TaskRepository
import com.example.planner.util.getFormattedCurrentTime
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

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
        _uiState.update { currentState -> currentState.copy(taskList = getTasks(currentState.getFilterType(selectedDate),selectedDate), selectedDate = selectedDate) }
   }

      fun getTasksByDate(date:LocalDate): Flow<List<Task>> =
        taskRepository.getTasksByDate(date.toString())

    fun onFilterSelected(filterType: FilterType){
        _uiState.value.filterMap[_uiState.value.selectedDate]=filterType
        _uiState.update { currentState -> currentState.copy(taskList = getTasks(filterType, _uiState.value.selectedDate)) }

        System.out.println(_uiState.value.taskList.toString()+"shiva***")

    }
     private fun getTasks(filterType: FilterType, date: LocalDate): Flow<List<Task>> =
          taskRepository.getTasksByTypeAndDate(filterType, date.toString())




}