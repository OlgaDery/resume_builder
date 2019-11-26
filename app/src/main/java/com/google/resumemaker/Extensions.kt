package com.google.resumemaker

import android.content.res.Resources
import com.google.resumemaker.entity.Education
import com.google.resumemaker.entity.Position
import com.google.resumemaker.entity.Profile
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(format: String): Date {
    val dateFormat = SimpleDateFormat(format, Locale.CANADA)
    return dateFormat.parse(this)
}

fun Date.toString(format: String, timeZone: TimeZone? = null): String {
    val simpleDateFormat = SimpleDateFormat(format, Locale.CANADA)
    if (timeZone != null) {
        simpleDateFormat.timeZone = timeZone
    }
    return simpleDateFormat.format(this)
}