package com.shiva.planner.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class PlannerDatabase: RoomDatabase() {

    abstract fun taskDao(): TaskDao
    companion object{
        @Volatile
        private var INSTANCE: PlannerDatabase? = null

        fun getDatabaseInstance(context:Context): PlannerDatabase {
            return INSTANCE ?: synchronized(this) {
                    Room.databaseBuilder(
                    context,
                    PlannerDatabase::class.java,
                    "planner_database").build().also{ INSTANCE =it}
            }
        }
    }
}