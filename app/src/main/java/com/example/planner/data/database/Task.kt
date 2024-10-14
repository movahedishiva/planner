package com.example.planner.data.database

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.planner.util.getFormattedCurrentTime
import java.time.LocalDate

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long=0,
    var title: String,
    var date: String,
    var time: String= getFormattedCurrentTime(),
    var isCompleted: Boolean = false
)
