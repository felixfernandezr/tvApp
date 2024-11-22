package com.example.tvapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvapp.adapters.showlistadapter.ShowListAdapter
import com.example.tvapp.api.model.ShowModel
import com.example.tvapp.api.model.ShowModelItem
import com.example.tvapp.databinding.ShowListLayoutBinding

class ShowList : AppCompatActivity() {

    private lateinit var binding: ShowListLayoutBinding
    private lateinit var showListAdapter: ShowListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ForecastDetail binding
        binding = ShowListLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieving data from MainActivity
        val showsData = intent.getParcelableArrayListExtra<ShowModelItem>("SHOWS_DATA")


        // Initialize ForecastDetail RecyclerView and Adapter
        showsData?.let {
                Log.d("ADAPTER", "data sent to viewholder")
                showListAdapter = ShowListAdapter(it) { show ->

                    binding.showListUI.visibility = View.GONE

                    // Handle item click: Show the details in fragment
                    val fragment = ShowDetailFragment.newInstance(show)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)  // Replace with your container ID
                        .addToBackStack(null)
                        .commit()
                }
                binding.showListRV.layoutManager = LinearLayoutManager(this)
                binding.showListRV.adapter = showListAdapter
        } ?: run {
            Log.d("ADAPTER", "data is null")
        }

        // Set up back button to return to MainActivity
        val backButton: Button = binding.backBtn
        backButton.setOnClickListener {

            finish() // Remove ShowList from the back stack
        }
    }

    // Adding functionality to OS back button
    override fun onBackPressed() {
        // Check if there are fragments in the back stack
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack() // Remove the last fragment
            showListLayout() // Making MainActivity UI visible
        } else {
            super.onBackPressed() // Exit the activity
        }

        // Show the main layout when there are no fragments in the container
        if (supportFragmentManager.backStackEntryCount == 0) {
            showListLayout()
        }
    }
    // Safely calling MainActivity binding property from fragment
    fun showListLayout() {
        binding.showListUI.visibility = View.VISIBLE
    }
}