package com.audify

import androidx.room.*

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSongs(arrayList: ArrayList<SongMetaData>)

    @Query("select * from SongMetaData")
    suspend fun fetchAllSongs(): List<SongMetaData>

    @Query("update SongMetaData set isFavourite = :favourite where id = :id")
    suspend fun update(favourite: Boolean, id: Long)
}