package com.google.resumemaker.ui.records


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.resumemaker.MainViewModel

import com.google.resumemaker.R
import com.google.resumemaker.databinding.FragmentModifyRecordBinding
import com.google.resumemaker.ui.EditItemBaseFragment
import kotlinx.android.synthetic.main.fragment_modify_record.*
import java.util.*

class ModifyRecordFragment : EditItemBaseFragment() {

    // private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentModifyRecordBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      //  viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_modify_record, container, false)
        binding.lifecycleOwner = activity
        binding.fragment = this
        binding.mode = viewModel.mode
        binding.record = viewModel.recordToEditOrCreate
        viewModel.createCopyOfRecord(viewModel.recordToEditOrCreate!!)
        return binding.root
    }

    override fun onEditFormButtonClick(buttonView: View) {
        if (buttonView.tag == getString(R.string.save)) {
            viewModel.addUpdateRemoveRecord(viewModel.recordToEditOrCreate!!, viewModel.mode!!, true)
        } else {
            viewModel.cancelCollectionUpdate(viewModel.mode!!, viewModel.copyOfRecordToModify!!)
        }
        val navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
        navController.popBackStack()
    }

    override fun onSelectNewDateClick(clickedView: View) {
        val calendar = Calendar.getInstance()
        val dom = calendar.get(Calendar.DAY_OF_MONTH)
        val mm = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val timePickerDialog = DatePickerDialog(activity!!, DatePickerDialog.OnDateSetListener()
        { view, val1, val2, val3 ->

            //TODO fix date handling
            System.out.println(" "+ val1 + " " + val2 + " " + val3)
            calendar.set(val1, val2, val3)
            if (clickedView.tag.toString() == resources.getString(R.string.start_date)) {
                startdate_edit_text.setText(val1.toString().plus("-").plus((val2+1).toString()).plus("-").plus(val3.toString()))

            } else {
                end_date_edit_text.setText(val1.toString().plus("-").plus((val2+1).toString()).plus("-").plus(val3.toString()))
            }
            //calendar.time.toString("yyyy-mm-dd")

        }, year, mm, dom)
        timePickerDialog.show()
    }
}
