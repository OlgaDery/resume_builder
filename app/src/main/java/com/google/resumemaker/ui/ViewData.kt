package com.google.resumemaker.ui

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.resumemaker.BR

class ViewData (var text: String?, val headerText: String? = null,
                val onUserInteraction: (data: String?, header: String?, view: View) -> Unit): BaseObservable() {

//    var userInput: String? = null
//        @Bindable get() {
//            if (field == null) {
//                return text
//            }
//            return field
//        }
//        set(value) {
//            if (value != field) {
//                field = value
//                notifyPropertyChanged(BR.userInput)
//            }
//        }

    fun onClick(view: View, header: String?, data: String?) {
        onUserInteraction(data, header, view)
    }
}