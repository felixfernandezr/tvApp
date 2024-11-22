package com.example.tvapp.adapters.showlistadapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tvapp.api.model.Show
import com.example.tvapp.api.model.ShowModel
import com.example.tvapp.api.model.ShowModelItem
import com.example.tvapp.databinding.ShowListItemBinding

class ShowListAdapter (
    private var payload: List<ShowModelItem>,
    private val onItemClick: (ShowModelItem) -> Unit
) : RecyclerView.Adapter<ShowListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowListViewHolder {
        val binding = ShowListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowListViewHolder, position: Int) {
        val item = payload[position]
        holder.render(item)

        // Set the click listener for items
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = payload.size

    fun updateData(newData: ShowModel) {
        payload = newData
        Log.d("FromAdapterData", "Data from adapter: $payload")
        notifyDataSetChanged()
    }
}