package com.example.planner

import android.app.Application
import com.example.planner.data.database.AppContainer
import com.example.planner.data.database.AppDataContainer

class PlannerApplication: Application()  {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}