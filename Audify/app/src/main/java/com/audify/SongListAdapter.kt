package com.audify

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SongListAdapter(val viewModel: MainViewModel) :
    RecyclerView.Adapter<SongListAdapter.SongListViewHolder>() {

    private var songList = arrayListOf<SongMetaData>()

    inner class SongListViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        fun bindView(position: Int, songMetaData: SongMetaData) {
            v.findViewById<TextView>(R.id.songName).text = songMetaData.name
            v.findViewById<TextView>(R.id.artistName).text = songMetaData.artistName
            val imageView = v.findViewById<ImageView>(R.id.favourite)
            if (songMetaData.isFavourite) {
                imageView.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                imageView.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
            imageView.setOnClickListener {
                if (!songMetaData.isFavourite) {
                    songMetaData.isFavourite = true
                    imageView.setImageResource(R.drawable.ic_baseline_favorite_24)
                    viewModel.updateFavourite(songMetaData)
                } else {
                    songMetaData.isFavourite = false
                    imageView.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    viewModel.updateFavourite(songMetaData)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_media, parent, false)
        return SongListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongListViewHolder, position: Int) {
        holder.bindView(position, songList[position])
    }

    fun updateList(list: ArrayList<SongMetaData>) {
        songList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return songList.size
    }
}