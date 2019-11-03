package com.google.resumemaker.entity

import android.graphics.Bitmap
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.resumemaker.BR
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

    var dob: String = ""
        @Bindable get() {
            if (dateOfBirth != null) {
                field = dateOfBirth!!.toString("yyyy-mm-dd")
            }
            return field
        }
        set(value) {
            if (value != field) {
                field = value
                dateOfBirth = field.toDate("yyyy-mm-dd")
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

}