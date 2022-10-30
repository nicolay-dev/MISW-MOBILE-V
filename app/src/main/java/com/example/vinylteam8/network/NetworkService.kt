package com.example.vinylteam8.network

import android.content.Context

class NetworkService constructor(context: Context){
    companion object{
        const val BASE_URL= "https://backend-vynils-tsdl.herokuapp.com/"
        var instance: NetworkService? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkService(context).also {
                    instance = it
                }
            }
    }
}