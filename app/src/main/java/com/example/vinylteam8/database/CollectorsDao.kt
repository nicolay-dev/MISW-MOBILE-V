package com.example.vinylteam8.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vinylteam8.models.Collectors

@Dao
interface CollectorsDao {

    @Query("SELECT * FROM collectors_table")
    fun getCollectors():List<Collectors>

    @Query("SELECT * FROM collectors_table WHERE collectorID = :idcollector")
    fun getCollector(idcollector:Int): Collectors

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(collectors: Collectors)

    @Query("DELETE FROM collectors_table")
    fun deleteAll(): Int
}