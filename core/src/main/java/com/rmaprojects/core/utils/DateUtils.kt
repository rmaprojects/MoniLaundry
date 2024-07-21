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
