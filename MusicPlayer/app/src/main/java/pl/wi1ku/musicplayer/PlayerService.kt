package pl.wi1ku.musicplayer

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.util.*


interface SongChangedListener {
    fun onSongChanged()
}

class PlayerService : Service() {



    inner class PlayerBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): PlayerService = this@PlayerService
    }

    // Binder given to clients
    private val binder = PlayerBinder()
    private val songQ = LinkedList<AudioFile>()
    private var currentTrackIndex = -1
    private val mListeners = ArrayList<SongChangedListener>()

    var mediaPlayer: MediaPlayer? = null
    var isPaused = true
    // Notification ID cannot be 0.
    val NOTIFICATION_ID = 1
    val NOTIFICATION_CHANNEL_ID = "MusicPlayerChannel"
    val NOTIFICATION_CHANNEL_NAME = "MusicPlayerChannel"


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val pendingIntent: PendingIntent =
            Intent(this, PlayerService::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        val notification: Notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getText(R.string.notification_title))
            .setContentText(getText(R.string.notification_message))
            .setSmallIcon(android.R.drawable.ic_lock_silent_mode_off)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(NOTIFICATION_ID, notification)

            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setOnCompletionListener { onCompletion(this) }


        }
            return START_NOT_STICKY

    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }


    override fun onDestroy() {
        mediaPlayer?.stop()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    fun addSongToQ(track: AudioFile){
        songQ.add(track)
        Toast.makeText(this, "Added ${track.name} to Queue", Toast.LENGTH_SHORT).show()
        if (songQ.size == 1){
            prepareNewTrack()
        }
    }

    private fun prepareNewTrack(){
        if (mediaPlayer != null && songQ.size != 0){
            if (mediaPlayer != null && currentTrackIndex < songQ.size-1){
                mediaPlayer!!.reset()
                currentTrackIndex++
                mediaPlayer!!.setDataSource(applicationContext, songQ[currentTrackIndex].uri)
                notifySongChanged()
                mediaPlayer!!.prepare()
            }
            else if (mediaPlayer != null && currentTrackIndex <= 0){
                mediaPlayer!!.reset()
                mediaPlayer!!.setDataSource(applicationContext, songQ[currentTrackIndex].uri)
                mediaPlayer!!.prepare()
            }
        }
    }

    fun onPlayPause(){
        if (mediaPlayer != null){
            if(!mediaPlayer!!.isPlaying){
                mediaPlayer!!.start()
                isPaused = false
            } else {
                mediaPlayer!!.pause()
                isPaused = true
            }
        }
    }

    private fun onCompletion(player: MediaPlayer){
        prepareNewTrack()
    }

    fun onNext(){
        if (mediaPlayer != null && currentTrackIndex >= 0){
            mediaPlayer!!.reset()
            prepareNewTrack()
            mediaPlayer!!.start()
            isPaused = false
        }
    }

    fun onPrevious(){
        if (mediaPlayer != null && currentTrackIndex > 0){
            mediaPlayer!!.reset()
            currentTrackIndex -= 2
            prepareNewTrack()
            mediaPlayer!!.start()
            isPaused = false
        }
    }

    fun onStop(){
        if (mediaPlayer != null){
            mediaPlayer!!.reset()
            isPaused = true
        }

    }

    fun onFF10(){
        if (mediaPlayer != null && currentTrackIndex >= 0){
            mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition + 10000)
        }
    }

    fun onFB10(){
        if (mediaPlayer != null && currentTrackIndex >= 0){
            mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition - 10000)
        }
    }

    fun registerListener(listener: SongChangedListener) {
        mListeners.add(listener)
    }

    fun unregisterListener(listener: SongChangedListener) {
        mListeners.remove(listener)
    }

    private fun notifySongChanged() {
        for (i in mListeners.size - 1 downTo 0) {
            mListeners[i].onSongChanged()
        }
    }

    fun currentSong() : AudioFile {
        return songQ[currentTrackIndex]
    }
}

