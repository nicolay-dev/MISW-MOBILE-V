package com.example.vinylteam8.repositories

import android.app.Application
import com.example.vinylteam8.models.Collectors
import com.example.vinylteam8.network.NetworkServiceAdapter

class CollectorRepository (val application: Application){
    suspend fun refreshData(): List<Collectors> {
        return NetworkServiceAdapter.getInstance(application).getCollectors()
        }
}