package com.google.resumemaker.ui.records


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation

import com.google.resumemaker.R
import com.google.resumemaker.databinding.FragmentViewRecordBinding
import com.google.resumemaker.ui.ViewItemBaseFragment

class ViewRecordFragment : ViewItemBaseFragment() {
    
    private lateinit var binding: FragmentViewRecordBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_record, container, false)
        binding.lifecycleOwner = activity
        binding.fragment = this
        binding.mode = viewModel.mode
        binding.record = viewModel.recordToEditOrCreate
        return binding.root
    }

    override fun onEditButtonClick() {
        val navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
        navController.navigate(R.id.view_record_to_modify_record_fragment)
    }

    fun oDeleteButtonClick() {
        viewModel.addOrRemoveRecord(viewModel.recordToEditOrCreate!!, viewModel.mode!!, false, true)
        val navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
        navController.popBackStack()
    }


}
