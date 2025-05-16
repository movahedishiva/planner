package com.shiva.planner

import android.app.Application
import com.shiva.planner.data.database.AppContainer
import com.shiva.planner.data.database.AppDataContainer

class PlannerApplication: Application()  {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}