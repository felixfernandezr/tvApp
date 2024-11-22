package com.example.tvapp.adapters.mainadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tvapp.database.ShowDbItem
import com.example.tvapp.databinding.ActivityMainItemBinding


class MainAdapter(private var item: List<ShowDbItem>) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    inner class MainViewHolder(val binding: ActivityMainItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ActivityMainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val show = item[position]
        holder.binding.showQuery.text = "${show.title}"
    }

    override fun getItemCount(): Int = item.size

    fun updateData(newHistoryList: List<ShowDbItem>) {
        item = newHistoryList
        notifyDataSetChanged()
    }
}