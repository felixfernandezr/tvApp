package com.example.tvapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ShowDAO {

    @Insert
    suspend fun insert(query: ShowDbItem)

    @Query("SELECT * FROM show_history ORDER BY id DESC")
    suspend fun getSearchedShows(): List<ShowDbItem>

}