package com.example.bin.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bin.data.model.DataCache

@Dao
interface AppRoomDao {

    @Query("SELECT * FROM item_table")
    fun fetchAllBins(): LiveData<List<DataCache>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBin(dataCache: DataCache)
}