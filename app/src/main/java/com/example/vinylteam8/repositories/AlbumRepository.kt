package com.example.vinylteam8.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinylteam8.models.Album
import com.example.vinylteam8.network.NetworkServiceAdapter

class AlbumRepository (val application: Application){
    suspend fun refreshData(): List<Album> {
        return NetworkServiceAdapter.getInstance(application).getAlbums()
        }
}