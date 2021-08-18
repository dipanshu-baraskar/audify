package com.audify

import android.app.Application
import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(val app: Application) : AndroidViewModel(app) {
    private val _songList = MutableLiveData<ArrayList<SongMetaData>>()
    val songList = _songList as LiveData<ArrayList<SongMetaData>>

    fun fetchSongList(ctx: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            var allSongs = SongsDatabase.getInstance(app).dao().fetchAllSongs()
            if (allSongs.isNotEmpty()) {
                _songList.postValue(ArrayList(allSongs))
            } else {
                val songsMetaDataList = arrayListOf<SongMetaData>()
                val contentR = ctx.contentResolver
                val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                val cursor = contentR.query(uri, null, null, null, null)
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {
                            val id =
                                cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                            val title =
                                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                            val artist =
                                cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))

                            val songMetaData = SongMetaData(id, title, artist)
                            songsMetaDataList.add(songMetaData)

                        } while (cursor.moveToNext())
                    }
                    cursor.close()
                    SongsDatabase.getInstance(app).dao().insertAllSongs(songsMetaDataList)

                    _songList.postValue(songsMetaDataList)
                }
            }
        }
    }

    fun updateFavourite(songMetaData: SongMetaData) {
        viewModelScope.launch(Dispatchers.IO) {
            SongsDatabase.getInstance(app).dao().update(songMetaData.isFavourite, songMetaData.id)
        }
    }


}