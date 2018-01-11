package io.github.joseluiscd.seagull.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.joseluiscd.seagull.R
import io.github.joseluiscd.seagull.model.Track

/**
 * Created by joseluis on 11/01/18.
 */

class TrackArrayAdapter(var tracks: Array<Track>? = null)
    : RecyclerView.Adapter<TrackViewHolder>()
{

    override fun getItemCount(): Int {
        return tracks?.size ?: 0
    }

    override fun onBindViewHolder(viewHolder: TrackViewHolder, position: Int) {
        val t = tracks?.get(position)
        viewHolder.title.text = t?.title
        viewHolder.artist.text = t?.artist
        viewHolder.album.text = t?.album

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TrackViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.track_view, parent, false)
        return TrackViewHolder(itemView)
    }

}
