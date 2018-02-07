package io.github.joseluiscd.seagull.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.joseluiscd.seagull.R
import io.github.joseluiscd.seagull.model.Album

/**
 * Created by joseluis on 7/02/18.
 */

class AlbumArrayAdapter(albums: Array<Album>? = null)
    : ArrayAdapter<Album, AlbumViewHolder>(albums)
{
    override fun createView(parent: ViewGroup?): View
        = LayoutInflater.from(parent?.context).inflate(R.layout.album_view, parent, false)

    override fun createViewHolder(item: View, clickListener: ClickListener?, menuListener: MenuListener?): AlbumViewHolder
        = AlbumViewHolder(item, clickListener, menuListener)

}