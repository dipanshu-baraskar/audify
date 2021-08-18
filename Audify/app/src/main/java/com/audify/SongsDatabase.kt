package com.audify

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SongMetaData::class], version = 1)
abstract class SongsDatabase : RoomDatabase() {
    companion object {
        private const val databaseName = "app.db"

        @Volatile
        private var instance: SongsDatabase? = null

        fun getInstance(ctx: Application): SongsDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildInstance(ctx).also { instance = it }
            }
        }

        private fun buildInstance(ctx: Context): SongsDatabase {
            return Room.databaseBuilder(ctx, SongsDatabase::class.java, databaseName)
                .allowMainThreadQueries().build()
        }
    }

    abstract fun dao(): SongDao
}