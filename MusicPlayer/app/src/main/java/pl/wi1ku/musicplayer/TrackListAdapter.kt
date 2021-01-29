package pl.wi1ku.musicplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TrackListAdapter (private val tracks: List<AudioFile>, private val clickListener: (AudioFile) -> Unit) :
    RecyclerView.Adapter<TrackListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val track: TextView
        val artist: TextView
        init {
            // Define click listener for the ViewHolder's View.
            track = view.findViewById(R.id.tn_tv)
            artist = view.findViewById(R.id.a_tv)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.track_layout, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.track.text = tracks[position].name
        viewHolder.artist.text = tracks[position].artist
        viewHolder.itemView.setOnClickListener { clickListener(tracks[position]) }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = tracks.size

}
