package pl.wi1ku.musicplayer

import android.Manifest
import android.content.ComponentName
import android.content.ContentUris
import android.content.Intent
import android.content.ServiceConnection
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_main.*
import pl.wi1ku.musicplayer.databinding.ActivityMainBinding
import java.net.URI


class MainActivity : AppCompatActivity() {

    var nowPlaying: AudioFile? = null
    private lateinit var mService: PlayerService
    private var mBound: Boolean = false


    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as PlayerService.PlayerBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestReadPermission()
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val linearLayoutManager = LinearLayoutManager(this)

        binding.apply {
            val trackListAdapter = TrackListAdapter(obtainTrackList()) { audioFile ->  this@MainActivity.nowPlaying = audioFile  }
            trackList.layoutManager = linearLayoutManager
            trackList.adapter = trackListAdapter
            SongName.text = "Not playing anything ATM"
        }
        binding.startBtn.setOnClickListener {
            if (nowPlaying != null){
                binding.SongName.text = nowPlaying!!.name
                Intent(this, PlayerService::class.java).also { intent ->
                    intent.putExtra("URI", nowPlaying!!.uri.toString())
                    startService(intent)
                }
            }
        }
        binding.StopBtn.setOnClickListener {
            Intent(this, PlayerService::class.java).also { intent ->
                stopService(intent)
            }
        }

        binding.resetBtn.setOnClickListener {
            Intent(this, PlayerService::class.java).also { intent ->
                stopService(intent)
                binding.SongName.text = nowPlaying!!.name
                intent.putExtra("URI", nowPlaying!!.uri.toString())
                startService(intent)
            }
        }

    }

    fun obtainTrackList() : List<AudioFile>{
        val trackList = mutableListOf<AudioFile>()
        val contentResolver = contentResolver
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val name: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                val album: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                val artist: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val duration: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                val uri: Uri =
                    ContentUris
                        .withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns._ID)))
                trackList.add(AudioFile(name, album, artist, duration, uri))

            } while (cursor.moveToNext())
        }
        return trackList
    }

    private fun requestReadPermission(){
        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(
                    this@MainActivity,
                    "Permission Denied\n$deniedPermissions",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        TedPermission.with(this)
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
            .check()
    }

}