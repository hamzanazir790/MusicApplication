package com.example.myapplication.ui

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicstask.adapter.SongsAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAddFavBinding
import com.example.myapplication.models.Songs

class Addfav_Activity : AppCompatActivity(){

    private lateinit var binding:ActivityAddFavBinding
    private lateinit var musicAdapter: SongsAdapter
    var songPosition:Int=0
    var mediaPlayer: MediaPlayer? =null
    var isPlaying:Boolean=false

    companion object{
        private lateinit var list: ArrayList<Songs>
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFavBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intilizaLayout()

        binding.ivPlay.setOnClickListener{

            if (isPlaying) pausMusic()
            else playMusic()


        }
        binding.ivForward.setOnClickListener{
            preNextSong(increment = false)
        }
        binding.ivBackward.setOnClickListener{
            preNextSong(increment = true)
        }

        binding.ivMenu.setOnClickListener{
            val intent= Intent(this@Addfav_Activity,MyMusuc::class.java )
            startActivity(intent)
        }


        binding.ivLike.setOnClickListener {

        }
    }


    private fun setLayout(){
        binding.tvSongName.text= list[songPosition].songTitle
        binding.tvArtistName.text= list[songPosition].songa_rtist
    }

    private fun playSong(){

       try {
           if (mediaPlayer==null) mediaPlayer= MediaPlayer()
           mediaPlayer!!.reset()
           mediaPlayer!!.setDataSource(list[songPosition].songPath)
           mediaPlayer!!.prepare()
           mediaPlayer!!.start()
           isPlaying=true
           binding.ivPlay.setImageResource(R.drawable.paus)

       }catch (e:Exception)
       {
           return
       }

    }

    private fun intilizaLayout()
    {

        songPosition=intent.getIntExtra("index",0)
        when(intent.getStringExtra("class")){
            "SongsAdapter" ->{
                list= ArrayList()
                list.addAll(MyMusuc.list)
                setLayout()
                playSong()



            }
        }
    }

    private fun playMusic(){
        binding.ivPlay.setImageResource(R.drawable.paus)
        isPlaying=true
        mediaPlayer!!.start()
    }
    private fun pausMusic(){
        binding.ivPlay.setImageResource(R.drawable.playmusic)
        isPlaying=false
        mediaPlayer!!.pause()
    }

    private fun preNextSong(increment:Boolean){

        if (increment)
        {
            setSongPostion(increment = true)
            playSong()
            setLayout()

        }
        else
            setSongPostion(increment = false)
        playSong()
        setLayout()

    }
    private fun setSongPostion(increment: Boolean){
        if(increment){
                if(list.size - 1 ==songPosition)
                    songPosition=0
            else ++songPosition
        }
        else{
            if (0==songPosition)
                songPosition= list.size-1
            else --songPosition
        }
    }

//    private fun setUpRecyclerView() {
//        list = mutableListOf()
//        musicAdapter = SongsAdapter(list)
//        binding.rvFavSong.apply {
//            layoutManager = LinearLayoutManager(this@Addfav_Activity, RecyclerView.HORIZONTAL, false)
//            adapter = musicAdapter
//        }
//    }
    }
