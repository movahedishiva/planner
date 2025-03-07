package com.example.planner.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("SELECT * FROM task_table ORDER BY id ASC")
     fun getAllTasks(): Flow<MutableList<Task>>

     @Query("Select * FROM task_table WHERE id=:id")
     fun getTask(id: Long): Flow<Task>

    @Query("SELECT * FROM task_table Where date=:date ORDER BY id ASC")
    fun getTasksByDate(date:String): Flow<List<Task>>

    @Query("SELECT * FROM task_table Where isCompleted=:isCompleted AND date=:date ORDER BY id ASC")
    fun getTasksByTypeAndDate(isCompleted:Boolean, date: String): Flow<List<Task>>

}