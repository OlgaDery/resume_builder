package com.google.resumemaker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.google.resumemaker.R

class RecyclerViewAdapter: RecyclerView.Adapter<BindingViewHolder>() {

    var items = mutableListOf<ViewData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return BindingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.recycler_view_item
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        holder.bind(items[position])
    }


}