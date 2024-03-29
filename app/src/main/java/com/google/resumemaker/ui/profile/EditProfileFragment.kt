package com.google.resumemaker.ui.profile


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
import com.google.resumemaker.databinding.FragmentEditProfileBinding
import com.google.resumemaker.toString
import com.google.resumemaker.ui.EditItemBaseFragment
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import java.util.*

class EditProfileFragment : EditItemBaseFragment() {

    private lateinit var binding: FragmentEditProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
        binding.lifecycleOwner = activity
        binding.profile = viewModel.resume!!.profile
        binding.fragment = this
        return binding.root
    }

    override fun onEditFormButtonClick(buttonView: View) {
        if (buttonView.tag == getString(R.string.cancel)) {
            viewModel.updateProfileOrCancelUpdate(true)
        } else {
            if (!viewModel.resume!!.profile.setUp) {
                Toast.makeText(activity, getString(R.string.fill_up_fields), Toast.LENGTH_LONG).show()
                return
            }
            viewModel.updateProfileOrCancelUpdate(false)
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
            dob_edit_text.setText(calendar.time.toString(MainActivity.DATE_FORMAT))

        }, year, mm, dom)
        timePickerDialog.show()
    }

}
