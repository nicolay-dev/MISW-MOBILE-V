package com.example.vinylteam8.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collectors_table")
data class Collectors (
    @PrimaryKey val collectorID:Int,
    val name:String,
    val telephone:String,
    val email:String
    )

data class CollectorDetails (
    @PrimaryKey val collectorID:Int,
    val name:String,
    val telephone: String,
    val email:String
    )
