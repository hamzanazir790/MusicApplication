package com.example.musicstask.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.models.Songs
import com.example.myapplication.models.formateDuration
import com.example.myapplication.ui.Addfav_Activity
import com.example.myapplication.ui.MyMusuc


class AllSongsAdapter(private val context: Context,private val songsList: List<Songs>) :
    RecyclerView.Adapter<AllSongsAdapter.AllSongsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllSongsViewHolder {
        return AllSongsViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.items_all_musics, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AllSongsViewHolder, position: Int) {
        val songsList = songsList[holder.adapterPosition]
        holder.tv_all_SongTitle.text = songsList.songTitle
        holder.tv_all_songArtist.text=songsList.songa_rtist
        holder.tvDuration.text= formateDuration(songsList.songDuration)
        holder.itemView.setOnClickListener{

            val intent= Intent(context, Addfav_Activity::class.java )

            intent.putExtra("index",position)
            intent.putExtra("class","SongsAdapter")

            ContextCompat.startActivity(context,intent,null)
        }

        Glide.with(context)
            .load(songsList.imgUri)
            .apply(RequestOptions().placeholder(R.drawable.centerimage2).centerCrop())
            .into(holder.imageSongs)
    }

    override fun getItemCount() = songsList.size

    inner class AllSongsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_all_SongTitle: TextView = itemView.findViewById(R.id.items_song_name)
        val tv_all_songArtist : TextView = itemView.findViewById(R.id.items_song_artist)
        val imageSongs:ImageView = itemView.findViewById(R.id.iv_all_musics)
        val tvDuration:TextView=itemView.findViewById(R.id.tv_duration)


    }


}

