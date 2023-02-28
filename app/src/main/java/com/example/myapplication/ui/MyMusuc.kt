package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicstask.adapter.AllSongsAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMyMusucBinding
import com.example.myapplication.models.Songs
import java.io.File

class MyMusuc : AppCompatActivity() {

    private lateinit var binding: ActivityMyMusucBinding
    private lateinit var allSongsAdapter: AllSongsAdapter

    companion object{
         lateinit var list: ArrayList<Songs>
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_musuc)
        requestRuntimeermission()
        binding=ActivityMyMusucBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Fro Recycler View
        setUpRecyclerView()



    }

    private fun requestRuntimeermission(){
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),13)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==13){
            if (grantResults.isNotEmpty()&& grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this,"Permission Is Granted",Toast.LENGTH_SHORT).show()
            else
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),13)
        }

    }

    private fun setUpRecyclerView() {

//        list = mutableListOf(
//            Songs("Khudgharzi","Song1","Album 1","Artist 1","gsgsdf"),
//            Songs("Khudgharzi","Song1","Album 1","Artist 1","gsgsdf"),
//            Songs("Khudgharzi","Song1","Album 1","Artist 1","gsgsdf"),
//            Songs("Khudgharzi","Song1","Album 1","Artist 1","gsgsdf"),
//
//            )
       list=getAllsongs()
        allSongsAdapter = AllSongsAdapter(this@MyMusuc,list)
        binding.rvAllSongs.hasFixedSize()
        binding.rvAllSongs.setItemViewCacheSize(14)
        binding.rvAllSongs.apply { layoutManager = LinearLayoutManager(this@MyMusuc)
            adapter = allSongsAdapter

            binding.totalSongs.text="Total Song :" + allSongsAdapter.itemCount
        }
    }


    @SuppressLint("Range")
    private fun getAllsongs():ArrayList<Songs> {
        val tempList=ArrayList<Songs>()
        val selection =MediaStore.Audio.Media.IS_MUSIC + " !=0"
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID
        )
        val cursor=this.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,
        MediaStore.Audio.Media.DATE_ADDED + " DESC", null)
        if (cursor!=null) {
            if (cursor.moveToFirst())
                do {
                    val tileC=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val idC=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    val albummC=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val artistsC=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val pathC=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val durationC=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val albumID=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)).toString()
                    val uri=Uri.parse("content://media/external/audio/albumart")
                    val artUriC=Uri.withAppendedPath(uri,albumID).toString()

                    val songs = Songs(songId = idC, songTitle = tileC,songAlbum =albummC, songa_rtist = artistsC, songPath = pathC,songDuration=durationC, imgUri = artUriC)
                    val file= File(songs.songPath)
                    if (file.exists())
                        tempList.add(songs)


                }while (cursor.moveToNext())
            cursor.close()
        }


        return tempList
    }
}