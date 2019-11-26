package com.google.resumemaker.entity

import android.content.res.Resources
import com.google.resumemaker.R
import java.util.*

data class Education(val id: String = UUID.randomUUID().toString(), val profileID: String): BaseRecord() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Education

        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        val result = id.hashCode()
        return result
    }

    fun toString(res: Resources): String {
        return header.plus(" ").plus(res.getString(R.string.at)).plus(" ").plus(organizationName ?: res.getString(
            R.string.data_not_set)).plus("\n")
            .plus(res.getString(R.string.faculty_name)).plus(" ").plus(facultyName ?: res.getString(R.string.data_not_set)).plus("\n")
            .plus(res.getString(R.string.location)).plus(" ").plus(location ?: res.getString(R.string.data_not_set)).plus("\n")
            .plus(res.getString(R.string.time)).plus(" ").plus(startDateText ?: res.getString(R.string.data_not_set)).plus(" - ").plus(endDateText).plus("\n")
            .plus(res.getString(R.string.description).plus(" ").plus(description ?: res.getString(R.string.data_not_set)).plus("\n")
                .plus(res.getString(R.string.edit_edu_achievement)).plus(" ").plus(achievementLevel ?: "").plus("\n"))
    }

    fun copyClassFields(): Education {
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
}