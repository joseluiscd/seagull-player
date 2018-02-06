package io.github.joseluiscd.seagull.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.github.joseluiscd.seagull.R
import io.github.joseluiscd.seagull.model.Track

/**
 * Created by joseluis on 11/01/18.
 */

class TrackArrayAdapter(var tracks: Array<Track>? = null)
    : Adapter<TrackViewHolder, Track>()
{
    override fun getItemAt(pos: Int): Track {
        return tracks?.get(pos) ?: Track()
    }

    override fun getItemCount(): Int {
        return tracks?.size ?: 0
    }

    override fun onBindViewHolder(viewHolder: TrackViewHolder, position: Int) {
        val t = tracks?.get(position)
        if(t != null) viewHolder.fillTrack(t)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TrackViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.track_view, parent, false)
        return TrackViewHolder(itemView, clickListener, menuListener)
    }

}
