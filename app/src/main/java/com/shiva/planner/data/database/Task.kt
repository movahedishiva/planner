package com.shiva.planner.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shiva.planner.util.getFormattedCurrentTime

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long=0,
    var title: String,
    var date: String,
    var time: String= getFormattedCurrentTime(),
    var isCompleted: Boolean = false
)
