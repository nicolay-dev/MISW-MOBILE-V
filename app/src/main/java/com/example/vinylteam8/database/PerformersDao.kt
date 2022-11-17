package com.example.vinylteam8.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vinylteam8.models.Performer

@Dao
interface PerformersDao {

    @Query("SELECT * FROM performers_table")
    fun getPerformer():List<Performer>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(performer: Performer)

    @Query("DELETE FROM performers_table")
    fun deleteAll(): Int
}