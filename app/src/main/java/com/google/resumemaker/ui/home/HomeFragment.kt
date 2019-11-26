package com.google.resumemaker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.resumemaker.MainViewModel
import com.google.resumemaker.R
import com.google.resumemaker.databinding.FragmentHomeBinding
import android.content.Intent
import androidx.core.content.FileProvider
import android.net.Uri
import java.io.File


class HomeFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        viewModel.file.observe(activity!!, Observer {

            if (savedInstanceState == null) {
                createShareIntent(file = it)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = activity
        binding.resume = viewModel.resume
        binding.resumeFound = viewModel.resume?.profile?.email != null
        binding.fragment = this

        return binding.root
    }

    fun onShareButtonClick() {
        viewModel.shareFile(viewModel.resume.toString())
    }

    fun createShareIntent(file: File) {
        val shareIntent = Intent(Intent.ACTION_SEND_MULTIPLE)
        shareIntent.type = "message/rfc822"

        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.resume))
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.resume_message))

        val uris = ArrayList<Uri>()
        val contentUri = FileProvider.getUriForFile(context!!, "com.google.resumemaker.fileprovider", file)
        uris.add(contentUri)
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)

        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        val msgStr = "Share"
        startActivity(Intent.createChooser(shareIntent, msgStr))
    }
}