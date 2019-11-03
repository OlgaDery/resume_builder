package com.google.resumemaker.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.resumemaker.MainViewModel
import com.google.resumemaker.R
import com.google.resumemaker.databinding.FragmentProfileBinding
import com.google.resumemaker.ui.RecyclerViewAdapter
import com.google.resumemaker.ui.ViewData
import com.google.resumemaker.ui.ViewItemBaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : ViewItemBaseFragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.lifecycleOwner = activity
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profile = viewModel.resume!!.profile
    }

    override fun onEditButtonClick() {
        val navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
        navController.navigate(R.id.profile_to_edit_profile_fragment)
    }
}