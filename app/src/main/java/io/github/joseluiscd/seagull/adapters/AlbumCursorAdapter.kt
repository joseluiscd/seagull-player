package io.github.joseluiscd.seagull.adapters

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.joseluiscd.seagull.R
import io.github.joseluiscd.seagull.model.Album
import io.github.joseluiscd.seagull.model.Track

/**
 * Created by joseluis on 7/02/18.
 */

class AlbumCursorAdapter(context: Context, cursor: Cursor)
    : CursorRecyclerViewAdapter<AlbumViewHolder, Album>(context, cursor)
{
    override fun getItemAt(pos: Int): Album {
        cursor.moveToPosition(pos)
        val a = Album()
        a.readFromCursor(cursor)
        return a
    }

    override fun onBindViewHolder(viewHolder: AlbumViewHolder, cursor: Cursor) {
        val t = Album()
        t.readFromCursor(cursor)
        viewHolder.fillItem(t)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.album_view, parent, false)
        return AlbumViewHolder(itemView, clickListener, menuListener)
    }

}