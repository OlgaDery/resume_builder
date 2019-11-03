package com.google.resumemaker.entity

import android.content.res.Resources
import com.google.resumemaker.toString
import java.lang.StringBuilder

class Resume (var profile: Profile) {

    var positions: MutableList<Position> = mutableListOf()
    var educations: MutableList<Education> = mutableListOf()
    @Transient lateinit var res: Resources

    val fullName: String
    get() {
        return "Full name: ".plus(profile.firstName.plus(" ").plus(profile.lastName)).plus("\n").plus("\n")
    }

    val dob: String
        get() {
            return "Date of birth: ".plus(profile.dateOfBirth.toString()).plus("\n").plus("\n")
        }

    val targetJob: String
        get() {
            return "Looking for position: ".plus(profile.targetPosition).plus("\n").plus("\n")
        }

    val experience: String
        get() {
            val builder = StringBuilder()
            builder.append("Experience: ").append("\n").append("\n")
            positions.forEach {
                builder.append(it.toString(res)).append("\n")
            }
            return builder.toString()
        }

    val education: String
        get() {
            val builder = StringBuilder()
            builder.append("Education: ").append("\n").append("\n")
            educations.forEach {
                builder.append(it.toString(res)).append("\n")
            }
            return builder.toString()
        }

    override fun toString(): String {
        return fullName.plus(dob).plus(targetJob).plus(experience).plus(education)
    }

}