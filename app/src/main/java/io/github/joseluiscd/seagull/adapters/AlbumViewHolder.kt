package io.github.joseluiscd.seagull.adapters

import android.support.v7.widget.RecyclerView
import android.view.ContextMenu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.github.joseluiscd.seagull.R
import io.github.joseluiscd.seagull.model.Album
import kotlinx.android.synthetic.main.album_view.view.*

/**
 * Created by joseluis on 7/02/18.
 */
class AlbumViewHolder(
        private val mView: View,
        private val clickListener: ClickListener? = null,
        private val menuListener: MenuListener? = null)
    : AbstractViewHolder<Album>(mView, clickListener, menuListener)

{
    internal var albumArt = mView.findViewById<ImageView>(R.id.album_art)
    internal var albumArtist = mView.findViewById<TextView>(R.id.album_artist)
    internal var albumTitle = mView.findViewById<TextView>(R.id.album_title)

    override fun fillItem(item: Album) {
        albumTitle.text = item.album
        albumArtist.text = item.albumartist

        Picasso.with(albumArt.context.applicationContext)
                .load(item.getAlbumArtURL(albumArt.context.applicationContext))
                .placeholder(R.drawable.lp)
                .into(this.albumArt)

    }

}