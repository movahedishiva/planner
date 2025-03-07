package com.example.planner.data.database

import com.example.planner.ui.TaskType
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

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

    override fun getTasksByTypeAndDate(taskType: TaskType, date: String): Flow<List<Task>> {
        return when(taskType){

            TaskType.COMPLETED -> taskDao.getTasksByTypeAndDate(true,date)
            TaskType.INCOMPLETE -> taskDao.getTasksByTypeAndDate(false,date)
            else -> taskDao.getTasksByDate(date)
        }
    }


    override fun getTask(id: Long): Flow<Task> =
        taskDao.getTask(id)

}