package com.example.tvapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvapp.adapters.mainadapter.MainAdapter
import com.example.tvapp.api.ApiResult
import com.example.tvapp.api.model.ShowModel
import com.example.tvapp.database.AppDataBase
import com.example.tvapp.database.ShowDAO
import com.example.tvapp.database.ShowDbItem
import com.example.tvapp.databinding.ActivityMainBinding
import com.example.tvapp.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var showsData : ShowModel
    private lateinit var showDao: ShowDAO
    private lateinit var appDatabase: AppDataBase
    private lateinit var mainAdapter: MainAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize MainActivity binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Room database
        appDatabase = AppDataBase.getDatabase(this)
        showDao = appDatabase.showDao()

        // Initialize MainActivity RecyclerView and Adapter
        mainAdapter = MainAdapter(listOf())
        binding.mainRV.layoutManager = LinearLayoutManager(this)
        binding.mainRV.adapter = mainAdapter

        // Observe API results
        viewModel.showResponse.observe(this) { result ->
            when (result) {
                is ApiResult.Loading -> {
                    binding.loadingTV.visibility = View.VISIBLE // Show loading text
                }
                is ApiResult.Success -> {
                    binding.loadingTV.visibility = View.GONE
                    binding.btnSeeResults.visibility = View.VISIBLE

                    val data = result.data
                    showsData = data

                    data[0].show?.name?.let { saveToDataBase(it) } // Save querys to DB

                    // Updates query list when response is successful
                    lifecycleScope.launch {
                        val querys = showDao.getSearchedShows()
                        mainAdapter.updateData(querys)
                    }
                }
                is ApiResult.Error -> {
                    binding.loadingTV.visibility = View.GONE
                    binding.btnSeeResults.visibility = View.GONE
                    binding.mainRV.visibility = View.GONE

                    // Show AlertDialog
                    AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("No shows found. Please try again.")
                        .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                        .show()
                }
            }
        }

        // SearchView listener for user input
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.getData(query) // Fetch data from ViewModel

                    hideKeyboard()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true // No action needed for text changes
            }
        })

        // Button listener for showing shows
        binding.btnSeeResults.setOnClickListener {
            // Start ShowList activity with weather data response
            val intent = Intent(this, ShowList::class.java).apply {
                putParcelableArrayListExtra("SHOWS_DATA", ArrayList(showsData))
                Log.d("INTENT", "$showsData")
            }
            startActivity(intent)
        }
    }

    // Save to Room DataBase
    private fun saveToDataBase(data: String) {

        // Check if the query (city) has been saved before in Room, if not, save it
        CoroutineScope(Dispatchers.IO).launch {
            runBlocking { // Prevents duplicates by waiting for coroutine to finish
                // Check if the query (city) has been saved before in Room
                val existingSearch = showDao.getSearchedShows().find {
                    it.title == data
                }

                // If the search history doesn't already contain this city, save it
                if (existingSearch == null) {
                    val searchHistoryItem = ShowDbItem(title = data)
                    showDao.insert(searchHistoryItem) // Insert into Room
                }
            }
        }
    }

    fun hideKeyboard() {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = this.currentFocus ?: View(this)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}