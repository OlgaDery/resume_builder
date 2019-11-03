package com.google.resumemaker.ui.records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.resumemaker.MainViewModel
import com.google.resumemaker.R
import com.google.resumemaker.databinding.FragmentRecordsBinding
import com.google.resumemaker.entity.Education
import com.google.resumemaker.entity.Position
import com.google.resumemaker.ui.RecyclerViewAdapter
import com.google.resumemaker.ui.ViewData
import kotlinx.android.synthetic.main.fragment_records.*

class RecordsFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentRecordsBinding
    private var adapter: RecyclerViewAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_records, container, false)
        binding.lifecycleOwner = activity
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val destination = Navigation.findNavController(activity!!, R.id.nav_host_fragment).currentDestination?.label

        if (viewModel.mode == null || destination != viewModel.mode!!.label) {
            var mode: RecordFragmentMode? = null

            when (destination) {
                getString(R.string.menu_education) -> {
                    mode = RecordFragmentMode(EDU_MODE, destination.toString(), viewModel.resume!!.educations,
                        Education::class.java, resources)
                }
                getString(R.string.menu_position) -> {
                    mode = RecordFragmentMode(POSITION_MODE, destination.toString(), viewModel.resume!!.positions,
                        Position::class.java, resources)
                }
            }
            viewModel.mode = mode
        }
        binding.recordsExist = viewModel.mode!!.data.isNotEmpty()

        adapter = RecyclerViewAdapter()

        records_recycler_view.adapter = adapter
        records_recycler_view.layoutManager = LinearLayoutManager(activity).apply {
            orientation = RecyclerView.VERTICAL
        }

        if (viewModel.mode!!.data.isNotEmpty()) {

            val viewDataList = mutableListOf<ViewData>()
            viewModel.mode!!.data.forEach {
                viewDataList.add(ViewData(text = it.description, headerText = it.header) { data, header, view ->
                    onRecyclerViewItemClick(data!!, header!!, view)
                })
            }
            adapter!!.items = viewDataList
            adapter!!.notifyDataSetChanged()
        }
    }

    //TODO add method to the parent fragment
    fun onAddButtonClick() {
        val navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
        navController.navigate(R.id.record_to_modify_record_fragment)
        viewModel.setUpNewRecord()
    }

    fun onRecyclerViewItemClick(data: String, header: String, view: View) {
        viewModel.mode!!.data.forEach {
            if (data == it.description) {
                viewModel.setUpNewRecord(it)
                val navController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                navController.navigate(R.id.record_to_view_record_fragment)
                return
            }
        }
    }

    companion object {
        const val EDU_MODE = "education modeName"
        const val PROJECT_MODE = "education modeName"
        const val POSITION_MODE = "position modeName"
    }
}