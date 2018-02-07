package io.github.joseluiscd.seagull.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.joseluiscd.seagull.R
import io.github.joseluiscd.seagull.model.Track

/**
 * Created by joseluis on 11/01/18.
 */

class TrackArrayAdapter(tracks: Array<Track>? = null)
    : ArrayAdapter<Track, TrackViewHolder>(tracks)
{
    override fun createView(parent: ViewGroup?): View
        = LayoutInflater.from(parent?.context).inflate(R.layout.track_view, parent, false)


    override fun createViewHolder(item: View, clickListener: ClickListener?, menuListener: MenuListener?): TrackViewHolder
        = TrackViewHolder(item, clickListener, menuListener)

}
