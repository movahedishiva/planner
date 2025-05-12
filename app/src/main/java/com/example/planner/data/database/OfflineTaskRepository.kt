package com.example.planner.data.database

import com.example.planner.ui.FilterType
import kotlinx.coroutines.flow.Flow

class OfflineTaskRepository(private val taskDao: TaskDao) : TaskRepository {
    override suspend fun addTask(task: Task) =
        taskDao.addTask(task)


    override suspend fun deleteTask(task: Task) =
        taskDao.deleteTask(task)


    override suspend fun updateTask(task: Task) =
        taskDao.updateTask(task)


    override fun getAllTasks(): Flow<MutableList<Task>> =
        taskDao.getAllTasks()

    override fun getTasksByDate(date: String): Flow<List<Task>> =
        taskDao.getTasksByDate(date)

    override fun getTasksByTypeAndDate(filterType: FilterType, date: String): Flow<List<Task>> {
        return when(filterType){

            FilterType.COMPLETED -> taskDao.getTasksByTypeAndDate(true,date)
            FilterType.INCOMPLETE -> taskDao.getTasksByTypeAndDate(false,date)
            else -> taskDao.getTasksByDate(date)
        }
    }


    override fun getTask(id: Long): Flow<Task> =
        taskDao.getTask(id)

}