package pl.wi1ku.musicplayer

import android.net.Uri

data class AudioFile(val name: String, val album: String, val artist: String, val duration: String, val uri: Uri) {
}