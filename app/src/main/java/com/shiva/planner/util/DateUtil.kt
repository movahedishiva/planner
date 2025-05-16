package com.shiva.planner.util

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale


fun getFormattedDate(date: LocalDate): String {
    return if (date == LocalDate.now()) "Today"
    else
        date.dayOfMonth.toString() + " " + date.month.getDisplayName(
            TextStyle.FULL,
            Locale.getDefault()
        ) + " " + date.year
}

fun getFormattedCurrentTime():String{
   /* val timeFormatter= DateTimeFormatter.ofPattern("hh:mm")
    return LocalTime.now().format(timeFormatter)*/
    return getFormattedTime(LocalTime.now())
}

fun getFormattedTime(time:LocalTime):String{
    val timeFormatter= DateTimeFormatter.ofPattern("HH:mm")
    return time.format(timeFormatter)
}