package com.example.planner.data.database

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface TaskRepository {
    suspend fun addTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun updateTask(task: Task)
    fun getAllTasks(): Flow<MutableList<Task>>
    fun getTasksByDate(date: String): Flow<List<Task>>
    fun getTask(id: Long): Flow<Task>
}