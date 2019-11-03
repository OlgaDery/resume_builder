package com.google.resumemaker

import android.content.res.Resources
import com.google.resumemaker.entity.Education
import com.google.resumemaker.entity.Position
import com.google.resumemaker.entity.Profile
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(format: String): Date {
    val dateFormat = SimpleDateFormat(format, Locale.CANADA)
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    return dateFormat.parse(this)
}

fun Date.toString(format: String, timeZone: TimeZone? = null): String {
    val simpleDateFormat = SimpleDateFormat(format, Locale.CANADA)
    if (timeZone != null) {
        simpleDateFormat.timeZone = timeZone
    }
    return simpleDateFormat.format(this)
}

fun Education.copyClassFields(): Education {
    val newClass = this.copy()
    newClass.facultyName = this.facultyName
    newClass.header = this.header
    newClass.description = this.description
    newClass.organizationName = this.organizationName
    newClass.achievementLevel = this.achievementLevel
    newClass.startDate = this.startDate
    newClass.endDate = this.endDate
    newClass.location = this.location
    return newClass
}

fun Education.toString(res: Resources): String {
    return header.plus(" at ").plus(organizationName ?: "No institution set").plus("\n")
        .plus("Faculty: ").plus(facultyName ?: "No faculty set").plus("\n")
        .plus("Location: ").plus(location ?: "No location set").plus("\n")
        .plus("Time: ").plus(startDateText ?: "No start date set").plus(" - ").plus(endDateText ?: "No end date").plus("\n")
        .plus("Description: ").plus(description ?: "No descriprion set").plus("\n")
        .plus("Certificate level: ").plus(achievementLevel ?: "").plus("\n")
}

fun Position.toString(res: Resources): String {
    return header.plus(" at ").plus(organizationName ?: "No company set").plus("\n")
        .plus("Location: ").plus(location ?: "No location set").plus("\n")
        .plus("Time: ").plus(startDateText ?: "No start date set").plus(" - ").plus(endDateText ?: "No end date").plus("\n")
        .plus("Description: ").plus(description ?: "No descriprion set").plus("\n")
        .plus("Achievements: ").plus(achievementLevel ?: "").plus("\n")
}

fun Position.copyClassFields(): Position {
    val newClass = this.copy()
    newClass.header = this.header
    newClass.description = this.description
    newClass.organizationName = this.organizationName
    newClass.achievementLevel = this.achievementLevel
    newClass.startDate = this.startDate
    newClass.endDate = this.endDate
    newClass.location = this.location
    return newClass
}

fun Profile.copyClassFields(): Profile {
    val newClass = this.copy()
    newClass.targetPosition = this.targetPosition
    newClass.lastName = this.lastName
    newClass.firstName = this.firstName
    newClass.email = this.email
    newClass.dateOfBirth = this.dateOfBirth
    return newClass
}