package com.shiva.planner.ui

import com.shiva.planner.data.database.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

data class TaskUiState(val taskList : Flow<List<Task>> , val selectedDate: LocalDate= LocalDate.now(), val filterMap: MutableMap<LocalDate,FilterType> = mutableMapOf()){

    fun getFilterType(): FilterType{
        return filterMap.getOrPut(selectedDate) { FilterType.ALL }
    }

    fun getFilterType(selectedDate: LocalDate): FilterType{
        return filterMap.getOrPut(selectedDate) { FilterType.ALL }
    }
}


enum class FilterType{
    COMPLETED,
    INCOMPLETE,
    ALL
}
