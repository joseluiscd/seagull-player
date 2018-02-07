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
        return TrackViewHolder(itemView, clickListener, menuListener)
    }

    override fun getItemCount(): Int = queue.tracks.size

    override fun onBindViewHolder(viewHolder: TrackViewHolder, position: Int) {
        val t = queue.tracks[position]
        viewHolder.fillItem(t)
    }

    override fun getItemAt(pos: Int): Track = queue.tracks[pos]
}