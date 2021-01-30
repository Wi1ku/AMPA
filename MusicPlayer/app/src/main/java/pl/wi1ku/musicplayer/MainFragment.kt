package pl.wi1ku.musicplayer

import android.content.*
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import pl.wi1ku.musicplayer.databinding.FragmentMainBinding


class MainFragment : Fragment(), SongChangedListener {

    private lateinit var mService: PlayerService
    private var mBound: Boolean = false
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        doStartService()
    }

    override fun onDetach() {
        unbindService()
        doStopService()
        super.onDetach()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        val linearLayoutManager = LinearLayoutManager(requireActivity().applicationContext)

        binding.apply {
            val trackListAdapter = TrackListAdapter(obtainTrackList()) { audioFile ->  mService.addSongToQ(audioFile) }
            trackList.layoutManager = linearLayoutManager
            trackList.adapter = trackListAdapter
            fabPlaypause.setOnClickListener {
                mService.onPlayPause()
                if(mService.isPaused){
                    binding.fabPlaypause.setImageResource(android.R.drawable.ic_media_play)
                }
                else {
                    binding.fabPlaypause.setImageResource(android.R.drawable.ic_media_pause)
                }

            }
            fabStop.setOnClickListener {
                mService.onStop()
            }
            fabNext.setOnClickListener {
                mService.onNext()
            }
            fabPrevious.setOnClickListener {
                mService.onPrevious()
            }
            fabFf.setOnClickListener {
                mService.onFF10()
            }
            fabRev.setOnClickListener {
                mService.onFB10()
            }


            seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onProgressChanged(
                    seekBar: SeekBar,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (mBound && mService.mediaPlayer != null && fromUser) {
                        mService.mediaPlayer!!.seekTo(progress * 1000)
                    }
                }
            })

        }
        return view
    }

    override fun onSongChanged(){
        updateSongtitle()
        updateSeekBar()
    }

    private fun updateSongtitle(){
        val currentSong = mService.currentSong()
        binding.apply {
            SongName.text = currentSong.name
        }
    }


    private fun updateSeekBar(){
        binding.apply {
            if (mService.mediaPlayer != null) {
                seekBar.max = mService.currentSong().duration.toInt()/1000
            }
            val mHandler = Handler()
            requireActivity().runOnUiThread(object : Runnable {
                override fun run() {
                    if (mService.mediaPlayer != null) {
                        val mCurrentPosition: Int = mService.mediaPlayer!!.currentPosition / 1000
                        seekBar.progress = mCurrentPosition
                    }
                    mHandler.postDelayed(this, 1000)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as PlayerService.PlayerBinder
            mService = binder.getService()
            mBound = true
            mService.registerListener(this@MainFragment)
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mService.unregisterListener(this@MainFragment)
            mBound = false
        }
    }

    private fun doStartService() {
        Intent(requireActivity().applicationContext, PlayerService::class.java).also { intent ->
            requireActivity().startService(intent)
            requireActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun unbindService() {
        if (mBound){
            requireActivity().unbindService(connection)
            mBound = false
        }
    }

    private fun doStopService(){
        Intent(requireActivity().applicationContext, PlayerService::class.java).also { intent ->
            requireActivity().stopService(intent)
        }
    }



    private fun obtainTrackList() : List<AudioFile>{
        val trackList = mutableListOf<AudioFile>()
        val contentResolver = requireActivity().contentResolver
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
                        .withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns._ID)))
                trackList.add(AudioFile(name, album, artist, duration, uri))

            } while (cursor.moveToNext())
        }
        return trackList
    }

    companion object {

        @JvmStatic fun newInstance() = MainFragment()
    }

}