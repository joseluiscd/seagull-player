package io.github.joseluiscd.seagull.adapters

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup

import io.github.joseluiscd.seagull.R
import io.github.joseluiscd.seagull.model.Track


class TrackCursorAdapter(context: Context, cursor: Cursor)
        : CursorRecyclerViewAdapter<TrackViewHolder, Track>(context, cursor) {

    override fun getItemAt(pos: Int): Track {
        cursor.moveToPosition(pos)
        val t = Track()
        t.readFromCursor(cursor)
        return t
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)
        return TrackViewHolder(itemView, clickListener, menuListener)
    }

    override fun onBindViewHolder(viewHolder: TrackViewHolder, cursor: Cursor) {
        val t = Track()
        t.readFromCursor(cursor)
        viewHolder.fillItem(t)
    }
}
