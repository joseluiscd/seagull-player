package io.github.joseluiscd.seagull.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.squareup.picasso.Picasso

import io.github.joseluiscd.seagull.R
import io.github.joseluiscd.seagull.model.Track

/**
 * Created by joseluis on 11/01/18.
 */

class TrackViewHolder(
        private val mView: View,
        private val clickListener: ClickListener? = null,
        private val menuListener: MenuListener? = null
)
    : AbstractViewHolder<Track>(mView, clickListener, menuListener) {

    internal var title: TextView
    internal var artist: TextView
    internal var album: TextView
    internal var albumArt: ImageView

    init {
        title = mView.findViewById(R.id.track_title)
        artist = mView.findViewById(R.id.track_artist)
        album = mView.findViewById(R.id.track_album)
        albumArt = mView.findViewById(R.id.track_album_image)
    }

    override fun fillItem(t: Track) {
        title.text = t.title
        artist.text = t.artist
        album.text = t.album

        Picasso.with(albumArt.context.applicationContext)
                .load(t.albumArtURL)
                .placeholder(R.drawable.lp)
                .into(this.albumArt)
    }
}
