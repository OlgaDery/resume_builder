package com.google.resumemaker.entity

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.resumemaker.BR
import com.google.resumemaker.MainActivity
import com.google.resumemaker.toDate
import com.google.resumemaker.toString
import java.util.*

//Base class to be implemented by different record classes (Education, Position and other possible classes)
abstract class BaseRecord: BaseObservable() {

    @get:Bindable
    var facultyName: String? = null
        set(value) {
            if (value != field) {
                field = value
                notifyPropertyChanged(BR.facultyName)
            }
        }

    @get:Bindable
    var achievementLevel: String? = null
        set(value) {
            if (value != field) {
                field = value
                notifyPropertyChanged(BR.achievementLevel)
            }
        }

    @get:Bindable
    var location: String? = null
        set(value) {
            if (value != field) {
                field = value
                notifyPropertyChanged(BR.location)
            }
        }

    val isCompleted: Boolean
        get() {
            if (description == null || startDate == null || header == null) {
                return false
            }
            return true
        }

    @get:Bindable
    var description: String? = null
        set(value) {
            if (value != field) {
                field = value
                notifyPropertyChanged(BR.description)
            }
        }

    var startDate: Date? = null
    var startDateText: String? = null
        @Bindable get() {
            if (startDate != null) {
                field = startDate!!.toString("MM/dd/yy")
            }
            return field
        }
        set(value) {
            if (value != field) {
                field = value
                startDate = field?.toDate("MM/dd/yy")
                notifyPropertyChanged(BR.startDateText)
            }
        }
    var endDate: Date? = null
    var endDateText: String = "now"
        @Bindable get() {
            if (endDate != null) {
                field = endDate!!.toString(MainActivity.DATE_FORMAT)
            }
            return field
        }
        set(value) {
            if (value != field) {
                field = value
                endDate = field.toDate(MainActivity.DATE_FORMAT)
                notifyPropertyChanged(BR.endDateText)
            }
        }

    @get:Bindable
    var header: String? = null
        set(value) {
            if (value != field) {
                field = value
                notifyPropertyChanged(BR.header)
            }
        }

    @get:Bindable
    var organizationName: String? = null
        set(value) {
            if (value != field) {
                field = value
                notifyPropertyChanged(BR.organizationName)
            }
        }

    val setUp: Boolean
        get() {
           return organizationName != null && header != null && startDate!= null && description != null
        }

}