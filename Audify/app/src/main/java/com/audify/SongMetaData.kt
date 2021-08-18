package com.audify

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SongMetaData(
    @PrimaryKey
    var id: Long,
    @ColumnInfo(name = "songName")
    var name: String? = null,
    @ColumnInfo(name = "artistName")
    var artistName: String? = null,
    var isFavourite : Boolean = false
)
