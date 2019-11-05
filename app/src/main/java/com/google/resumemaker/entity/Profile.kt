package com.google.resumemaker.entity

import android.graphics.Bitmap
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.resumemaker.BR
import com.google.resumemaker.MainActivity
import com.google.resumemaker.toDate
import com.google.resumemaker.toString
import java.util.*

data class Profile (val id: String = UUID.randomUUID().toString()): BaseObservable() {

    @get:Bindable var email: String? = null
        set(value) {
            if (value != field) {
                field = value
                notifyPropertyChanged(BR.email)
            }
        }

    @get:Bindable  var firstName: String? = null
        set(value) {
            if (value != field) {
                field = value
                notifyPropertyChanged(BR.firstName)
            }
        }

    @get:Bindable  var lastName: String? = null
        set(value) {
            if (value != field) {
                field = value
                notifyPropertyChanged(BR.lastName)
            }
        }

    @Transient var dob: String = ""
        @Bindable get() {
            if (dateOfBirth != null) {
                field = dateOfBirth!!.toString(MainActivity.DATE_FORMAT)
            }
            return field
        }
        set(value) {
            if (value != field) {
                field = value
                dateOfBirth = field.toDate(MainActivity.DATE_FORMAT)
                notifyPropertyChanged(BR.dob)
            }
        }

    var dateOfBirth: Date? = null
    @get:Bindable var targetPosition: String? = null
        set(value) {
            if (value != field) {
                field = value
                notifyPropertyChanged(BR.targetPosition)
            }
        }


    val setUp: Boolean
        get() {
            return dateOfBirth != null && email != null && firstName != null && lastName != null
        }

}