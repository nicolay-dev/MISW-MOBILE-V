package com.example.vinylteam8.network

import android.content.Context

class NetworkServiceAdapter constructor(context: Context){
    companion object{
        const val BASE_URL= "https://backend-vynils-tsdl.herokuapp.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }
}