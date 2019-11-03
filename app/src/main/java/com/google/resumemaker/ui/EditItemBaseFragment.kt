package com.google.resumemaker.ui


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import com.google.resumemaker.MainViewModel

import com.google.resumemaker.R
import java.util.*

abstract class EditItemBaseFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
    }

    abstract fun onEditFormButtonClick(buttonView: View)

    abstract fun onSelectNewDateClick(clickedView: View)

}
