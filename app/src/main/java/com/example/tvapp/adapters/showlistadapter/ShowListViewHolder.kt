package com.example.tvapp.adapters.showlistadapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.tvapp.R
import com.example.tvapp.api.model.ShowModel
import com.example.tvapp.api.model.ShowModelItem
import com.example.tvapp.databinding.ShowListItemBinding
import com.squareup.picasso.Picasso

class ShowListViewHolder(private val binding: ShowListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(show: ShowModelItem) {
        Log.d("FromViewHolderData", "Data from view holder: $show")

        binding.showTitle.text = show.show?.name ?: "No name found."

        // Load image using Picasso
        val imageUrl = show.show?.image?.medium // Assuming `image.medium` contains the URL
        if (imageUrl != null) {
            Picasso.get()
                .load(imageUrl)
                .into(binding.showImage) // Bind to your ImageView
        }
    }
}