package com.google.resumemaker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.google.resumemaker.MainViewModel

//parent class for the fragments that show up the edit forms to update single entity
abstract class EditItemBaseFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
    }

    abstract fun onEditFormButtonClick(buttonView: View)

    abstract fun onSelectNewDateClick(clickedView: View)

}
