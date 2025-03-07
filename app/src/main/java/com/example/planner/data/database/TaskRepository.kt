package com.example.planner.data.database

import com.example.planner.ui.TaskType
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TaskRepository {
    suspend fun addTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun updateTask(task: Task)
    fun getAllTasks(): Flow<MutableList<Task>>
    fun getTasksByDate(date: String): Flow<List<Task>>
    fun getTasksByTypeAndDate(taskType: TaskType, date: String): Flow<List<Task>>
    fun getTask(id: Long): Flow<Task>
}