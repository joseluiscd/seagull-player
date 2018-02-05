package io.github.joseluiscd.seagull.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.joseluiscd.seagull.R
import io.github.joseluiscd.seagull.media.Queue
import io.github.joseluiscd.seagull.model.Track

/**
 * Created by joseluis on 5/02/18.
 */
class TrackQueueAdapter(val queue: Queue): Adapter<TrackViewHolder, Track>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TrackViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.track_view, parent, false)
        return TrackViewHolder(itemView, clickListener)
    }

    override fun getItemCount(): Int = queue.tracks.size

    override fun onBindViewHolder(viewHolder: TrackViewHolder, position: Int) {
        val t = queue.tracks[position]
        viewHolder.title.text = t?.title
        viewHolder.artist.text = t?.artist
        viewHolder.album.text = t?.album
    }

    override fun getItemAt(pos: Int): Track = queue.tracks[pos]
}