package com.google.resumemaker.ui.records


import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.google.resumemaker.MainActivity

import com.google.resumemaker.R
import com.google.resumemaker.databinding.FragmentModifyRecordBinding
import com.google.resumemaker.toString
import com.google.resumemaker.ui.EditItemBaseFragment
import kotlinx.android.synthetic.main.fragment_modify_record.*
import java.util.*

class ModifyRecordFragment : EditItemBaseFragment() {

    private lateinit var binding: FragmentModifyRecordBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
            if (viewModel.recordToEditOrCreate!!.setUp) {
                viewModel.addOrRemoveRecord(viewModel.recordToEditOrCreate!!, viewModel.mode!!, true, false)
            } else {
                Toast.makeText(activity, getString(R.string.fill_up_fields), Toast.LENGTH_LONG).show()
                return
            }
        } else {
            viewModel.recordToEditOrCreate = viewModel.cancelCollectionUpdate(viewModel.mode!!, viewModel.copyOfRecordToModify!!)
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

            calendar.set(val1, val2, val3)
            if (clickedView.tag.toString() == resources.getString(R.string.start_date)) {
                startdate_edit_text.setText(calendar.time.toString(MainActivity.DATE_FORMAT))
            } else {
                end_date_edit_text.setText(calendar.time.toString(MainActivity.DATE_FORMAT))
            }

        }, year, mm, dom)
        timePickerDialog.show()
    }
}
