package com.google.resumemaker.entity

import android.content.res.Resources
import com.google.resumemaker.R
import java.lang.StringBuilder

//Class accumulating the data of other entity classes. "ToString" method represents the nicely-formatted data that can be saved
//in SharedPreferences, written to the file etc.

class Resume (var profile: Profile) {

    var positions: MutableList<Position> = mutableListOf()

    var educations: MutableList<Education> = mutableListOf()

    @Transient lateinit var res: Resources

    private val fullName: String
    get() {
        return res.getString(R.string.full_name).plus(" ").plus(profile.firstName.plus(" ").plus(profile.lastName)).plus("\n")
            .plus("\n")
    }

    val dob: String
        get() {
            return res.getString(R.string.dob).plus(" ").plus(profile.dob).plus("\n").plus("\n")
        }

    private val targetJob: String
        get() {
            return res.getString(R.string.looking_for_position).plus(" ").plus(profile.targetPosition).plus("\n").plus("\n")
        }

    private val experience: String
        get() {
            val builder = StringBuilder()
            builder.append(res.getString(R.string.experience)).append("\n").append("\n")
            positions.forEach {
                builder.append(it.toString(res)).append("\n")
            }
            return builder.toString()
        }

    private val education: String
        get() {
            val builder = StringBuilder()
            builder.append(res.getString(R.string.education)).append("\n").append("\n")
            educations.forEach {
                builder.append(it.toString(res)).append("\n")
            }
            return builder.toString()
        }

    override fun toString(): String {
        return fullName.plus(dob).plus(targetJob).plus(experience).plus(education)
    }
}