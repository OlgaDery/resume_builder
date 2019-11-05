package com.google.resumemaker.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.resumemaker.MainViewModel

abstract class ViewItemBaseFragment : Fragment() {
    //parent class for the fragments that show up the single entity related data (Profile, BaseRecord etc)

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
    }

    abstract fun onEditButtonClick()

}
