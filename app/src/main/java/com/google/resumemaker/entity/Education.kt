package com.google.resumemaker.entity

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
}