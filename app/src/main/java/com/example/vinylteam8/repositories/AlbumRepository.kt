package com.example.vinylteam8.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.vinylteam8.database.AlbumsDao
import com.example.vinylteam8.models.Album
import com.example.vinylteam8.network.NetworkServiceAdapter

class AlbumRepository (val application: Application, private val albumsDao: AlbumsDao){
    suspend fun refreshData(): List<Album> {
        var cached = albumsDao.getAlbums()
        return if (cached.isNullOrEmpty()) {
            val cm =
                application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE) {
                emptyList()
            } else NetworkServiceAdapter.getInstance(application).getAlbums()
        } else cached
    }

    suspend fun refreshDataDetails(albumId: Int): Album {
        var cached = albumsDao.getAlbum(albumId)
        return if (cached == null) {
           NetworkServiceAdapter.getInstance(application).getAlbum(albumId)
        } else cached
    }

}