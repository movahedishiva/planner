package com.example.planner.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.planner.PlannerApplication
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory

object AppViewModelProvider {

   /* fun factory(selectedDate: String) : ViewModelProvider.Factory = viewModelFactory {
        initializer {
            TaskViewModel(PlannerApplication().container.taskRepository, selectedDate)
        }
    }*/
    val Factory = viewModelFactory{

        // Initializer for TaskViewModel
        initializer {
            //val selectedDate:String
            TaskViewModel(PlannerApplication().container.taskRepository)
        }

    }
}


/**
 * Extension function to queries for [Application] object and returns an instance of
 * [PlannerApplication].
 */
fun CreationExtras.PlannerApplication(): PlannerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as PlannerApplication)