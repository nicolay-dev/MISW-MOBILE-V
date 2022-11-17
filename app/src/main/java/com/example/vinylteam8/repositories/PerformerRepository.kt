package com.example.vinylteam8.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.android.volley.VolleyError
import com.example.vinylteam8.database.PerformersDao
import com.example.vinylteam8.models.Performer
import com.example.vinylteam8.network.NetworkServiceAdapter


class PerformerRepository (val application: Application, private val performerDao: PerformersDao) {
    suspend fun refreshData(): List<Performer> {
        var cached = performerDao.getPerformer()
        return if (cached.isNullOrEmpty()) {
            val cm =
                application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE) {
                emptyList()
            }  else NetworkServiceAdapter.getInstance(application).getPerformers()
        } else cached
    }
}
