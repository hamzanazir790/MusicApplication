package com.example.myapplication.models

import java.util.concurrent.TimeUnit

data class Songs(

    val songId:String,
    val songTitle:String,
    val songAlbum:String,
    val songa_rtist:String,
    val songPath:String,
    val songDuration:Long,
    val imgUri:String
)

    fun formateDuration(duratin:Long):String
    {
        val minutes=TimeUnit.MINUTES.convert(duratin,TimeUnit.MILLISECONDS)
        val seconds=TimeUnit.SECONDS.convert(duratin,TimeUnit.MILLISECONDS)-
                minutes * TimeUnit.SECONDS.convert(1,TimeUnit.MINUTES)
        return String().format("%02d:%20d",minutes,seconds)

    }
