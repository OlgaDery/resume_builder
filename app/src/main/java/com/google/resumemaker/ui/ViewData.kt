package com.google.resumemaker.ui

import androidx.databinding.BaseObservable

//the class to represent some fields of entity classes in the recycler view items
class ViewData (val id: String, var text: String?, val headerText: String? = null,
                val onUserInteraction: (data: String?) -> Unit): BaseObservable() {


    fun onClick(data: String?) {
        onUserInteraction(data)
    }
}