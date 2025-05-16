package com.shiva.planner.util

import com.shiva.planner.data.database.Task



      internal fun getActivatedAndCompletedTaskState(tasks:List<Task>?): StateResult {

       return if(tasks.isNullOrEmpty()){
               StateResult(0f, 0f)

          }else{
                val numberOfTasks= tasks.size
                val numberOfCompleted=tasks.count{it.isCompleted}
                val activePercent=100*(numberOfTasks-numberOfCompleted)/numberOfTasks
                val completedPercent=100*numberOfCompleted/numberOfTasks

                return StateResult(completedTaskPercent = completedPercent.toFloat(), activeTaskPercent = activePercent.toFloat())

            }

        }

    data class StateResult(val completedTaskPercent:Float, val activeTaskPercent:Float)
