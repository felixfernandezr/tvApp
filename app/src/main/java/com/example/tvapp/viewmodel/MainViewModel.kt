package com.example.tvapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvapp.api.ApiResult
import com.example.tvapp.api.RetrofitClient
import com.example.tvapp.api.model.ShowModel
import com.example.tvapp.api.model.ShowModelItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val showApi = RetrofitClient.showApi
    private val _showResponse = MutableLiveData<ApiResult<ShowModel>>()
    val showResponse: LiveData<ApiResult<ShowModel>> = _showResponse

    fun getData(query: String){
        _showResponse.postValue(ApiResult.Loading) // Indicate loading state

        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = showApi.getShows(query)
                if (response.isSuccessful) {
                    Log.d("payload", response.body().toString())
                    // Check if the response body is not null and contains forecasts

                    response.body()?.let { showModel ->
                        _showResponse.postValue(ApiResult.Success(showModel)) // Post the list of ShowModelItem
                    } ?: run {
                        Log.d("VIEWMODEL", "Null response")

                        _showResponse.postValue(ApiResult.Error("Response body is null"))
                    }
                } else {

                    Log.d("VIEWMODEL", "response notSuccesful")
                    _showResponse.postValue(ApiResult.Error("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Log.e("VIEWMODEL", "Error fetching data", e)
                _showResponse.postValue(ApiResult.Error(e.message ?: "Unknown error"))
            }
        }
    }
}