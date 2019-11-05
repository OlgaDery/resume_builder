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
    return header.plus(" ").plus(res.getString(R.string.at)).plus(" ").plus(organizationName ?: res.getString(R.string.data_not_set)).plus("\n")
        .plus(res.getString(R.string.faculty_name)).plus(" ").plus(facultyName ?: res.getString(R.string.data_not_set)).plus("\n")
        .plus(res.getString(R.string.location)).plus(" ").plus(location ?: res.getString(R.string.data_not_set)).plus("\n")
        .plus(res.getString(R.string.time)).plus(" ").plus(startDateText ?: res.getString(R.string.data_not_set)).plus(" - ").plus(endDateText).plus("\n")
        .plus(res.getString(R.string.description).plus(" ").plus(description ?: res.getString(R.string.data_not_set)).plus("\n")
        .plus(res.getString(R.string.edit_edu_achievement)).plus(" ").plus(achievementLevel ?: "").plus("\n"))
}

fun Position.toString(res: Resources): String {
    return header.plus(" ").plus(res.getString(R.string.at)).plus(" ").plus(organizationName ?: res.getString(R.string.data_not_set)).plus("\n")
        .plus(res.getString(R.string.location)).plus(" ").plus(location ?: res.getString(R.string.data_not_set)).plus("\n")
        .plus(res.getString(R.string.time)).plus(" ").plus(startDateText ?: res.getString(R.string.data_not_set)).plus(" - ").plus(endDateText).plus("\n")
        .plus(res.getString(R.string.description)).plus(" ").plus(description ?: res.getString(R.string.data_not_set)).plus("\n")
        .plus(res.getString(R.string.edit_position_achievement)).plus(" ").plus(achievementLevel ?: "").plus("\n")
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