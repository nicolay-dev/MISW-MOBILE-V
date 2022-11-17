package com.example.vinylteam8

import android.app.Application
import com.example.vinylteam8.database.VinylRoomDatabase

class VinylsApplication: Application()  {

    val database by lazy { VinylRoomDatabase.getDatabase(this) }
}