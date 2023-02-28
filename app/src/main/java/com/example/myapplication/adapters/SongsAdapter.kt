package com.example.musicstask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.models.Songs


class SongsAdapter(private val songsList: List<Songs>) :
    RecyclerView.Adapter<SongsAdapter.MusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        return MusicViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.items_music_library, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val song = songsList[holder.adapterPosition]
        holder.tvSongTitle.text = song.songTitle
        holder.tv_songArtist.text=song.songa_rtist


    }

    override fun getItemCount() = songsList.size

    inner class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSongTitle: TextView = itemView.findViewById(R.id.tv_songName)
        val tv_songArtist : TextView = itemView.findViewById(R.id.tv_songArtist)
    }
}

