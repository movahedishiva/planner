package com.example.planner.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planner.R
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.Week
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun WeekCalendarSection(taskUiState: TaskUiState,onDaySelected:(LocalDate)->Unit) {

    val currentDate = remember { LocalDate.now() }
    val currentMonth = remember { YearMonth.now() }
    val startDate = remember { currentMonth.minusMonths(100).atStartOfMonth() } // Adjust as needed
    val endDate = remember { currentMonth.plusMonths(100).atEndOfMonth() } // Adjust as needed
   // val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library
    val daysOfWeek = remember { daysOfWeek() }

    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = daysOfWeek.first()

    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
       // colors = CardDefaults.cardColors(containerColor = colorResource(R.color.purple_50)),
        colors=CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = RectangleShape
        ) {
        Row(modifier = Modifier.padding(vertical = 16.dp)) {
           // var daySelected by remember { mutableStateOf(false) }
            WeekCalendar(
                contentPadding = PaddingValues(0.dp),
                state = state,
                dayContent = { Day(it, currentDate,onDaySelected, taskUiState) },
                weekHeader = {
                    DaysOfWeekTitle(
                        it,
                        daysOfWeek = daysOfWeek,
                        currentDate,
                        onDaySelected
                    ) // Use the title as month header
                }
            )
        }

    }
}


@Composable
fun DaysOfWeekTitle(week: Week, daysOfWeek: List<DayOfWeek>, currentDate: LocalDate, onDaySelected: (LocalDate) -> Unit) {

    val weekContainsCurrentDate = week.days.any { it.date == currentDate }
  Row(modifier = Modifier.fillMaxWidth()) {
        for (day in week.days) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 5.dp),
                textAlign = TextAlign.Center,
                text = day.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                fontWeight = if (day.date.dayOfWeek == currentDate.dayOfWeek && weekContainsCurrentDate) FontWeight.Bold else FontWeight.Normal,
                color = MaterialTheme.colorScheme.onPrimary /*if (day.date.dayOfWeek == currentDate.dayOfWeek && weekContainsCurrentDate)
                    MaterialTheme.colorScheme.onPrimary
                 else MaterialTheme.colorScheme.onPrimary*/
            )
        }

}
}

@Composable
fun Day(day: WeekDay, currentDate: LocalDate, onDaySelected: (LocalDate) -> Unit, taskUiState: TaskUiState) {


    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(
                onClick = {
                    onDaySelected(day.date)
                }
            )
            .then(
                if (day.date == currentDate && day.date != taskUiState.selectedDate) {
                    Modifier.background(color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6F), CircleShape)
                } else if(day.date == taskUiState.selectedDate){
                    Modifier.background(color = MaterialTheme.colorScheme.primaryContainer,CircleShape)
                }else{
                    Modifier.background(color =MaterialTheme.colorScheme.primary, CircleShape)
                }

            ), // This is important for square sizing!
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            fontWeight = if (day.date == currentDate) FontWeight.Bold else FontWeight.Normal,
            color= if(day.date == currentDate || day.date == taskUiState.selectedDate)
                MaterialTheme.colorScheme.onPrimaryContainer
            else
                MaterialTheme.colorScheme.onPrimary
        )
    }
}



@Preview
@Composable
private fun WeekCalendarScreenPreview() {
   // WeekCalendarSection()

}
