package com.shiva.planner.data.database

import android.content.Context

interface AppContainer {
    val taskRepository: TaskRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineTaskRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [TaskRepository]
     */
    override val taskRepository: TaskRepository by lazy {
        OfflineTaskRepository(PlannerDatabase.getDatabaseInstance(context).taskDao())
    }
}