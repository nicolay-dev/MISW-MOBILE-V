package com.example.vinylteam8.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.vinylteam8.database.AlbumsDao
import com.example.vinylteam8.database.CollectorsDao
import com.example.vinylteam8.models.AlbumDetails
import com.example.vinylteam8.models.CollectorDetails
import com.example.vinylteam8.models.Collectors
import com.example.vinylteam8.network.NetworkServiceAdapter

class CollectorRepository (val application: Application, private val collectorsDao: CollectorsDao){
    suspend fun refreshData(): List<Collectors> {
        var cached = collectorsDao.getCollectors()
        return if (cached.isNullOrEmpty()) {
            val cm =
                application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE) {
                emptyList()
            } else NetworkServiceAdapter.getInstance(application).getCollectors()
        } else cached
        }

    suspend fun refreshDataDetails(collectorId: Int): CollectorDetails{

        return NetworkServiceAdapter.getInstance(application).getCollector(collectorId)
    }
}