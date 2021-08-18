package com.audify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val adapter = SongListAdapter(viewModel)

        viewModel.songList.observe(this) { arrayList ->
            adapter.updateList(arrayList)
        }


        val recyclerView = findViewById<RecyclerView>(R.id.mainRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        recyclerView.adapter = adapter

        viewModel.fetchSongList(this)

    }
}