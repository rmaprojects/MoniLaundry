package com.rmaprojects.core.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale


fun getCurrentDate(): LocalDate {
    return LocalDate.now()
}

fun LocalDate.firstDayInMonth(): LocalDate? {
    return this.withDayOfMonth(1)
}

fun convertDateTimeToDateString(dateTimeString: String): String {
    val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSXXX", Locale.getDefault())
    val dateFormat = SimpleDateFormat("yyyy-MM-DD", Locale.getDefault())
    val parsedDate = dateTimeFormat.parse(dateTimeString)
    return dateFormat.format(parsedDate)
}
