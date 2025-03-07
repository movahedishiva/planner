package com.example.planner.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.planner.data.database.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate

data class TaskUiState(val taskList : Flow<List<Task>> , val selectedDate: LocalDate= LocalDate.now(), val taskType: TaskType =  TaskType.All)


enum class TaskType{
    COMPLETED,
    INCOMPLETE,
    All
}
