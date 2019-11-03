package com.google.resumemaker.entity

import java.util.*

data class Position(val id: String = UUID.randomUUID().toString()): BaseRecord() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Position
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

//    override fun toString(): String {
//        return header.plus(" at ").plus(organizationName ?: "No company set").plus("\n")
//            .plus("Location: ").plus(location ?: "No location set").plus("\n")
//            .plus("Time: ").plus(startDateText ?: "No start date set").plus(" - ").plus(endDateText ?: "No end date").plus("\n")
//            .plus("Description: ").plus(description ?: "No descriprion set").plus("\n")
//            .plus("Highlights: ").plus(achievementLevel ?: "").plus("\n")
//    }


}