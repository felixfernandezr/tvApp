package com.example.tvapp.api

import com.example.tvapp.api.model.ShowModel
import com.example.tvapp.api.model.ShowModelItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/shows")
    suspend fun getShows(
        @Query("q") query: String,
    ): Response<ShowModel>
}