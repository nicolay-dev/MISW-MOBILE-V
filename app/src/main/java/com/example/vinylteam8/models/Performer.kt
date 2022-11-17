package com.example.vinylteam8.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "performers_table")
data class Performer (

    @PrimaryKey val performerID:Int,
    val name:String,
    val image:String,
    val description:String,

)