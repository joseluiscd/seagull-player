package io.github.joseluiscd.seagull.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.joseluiscd.seagull.R;
import io.github.joseluiscd.seagull.TracksFragment.OnListFragmentInteractionListener;
import io.github.joseluiscd.seagull.db.TrackListener;
import io.github.joseluiscd.seagull.model.Track;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Track} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TrackCursorAdapter extends CursorRecyclerViewAdapter<TrackViewHolder>{

    public TrackCursorAdapter(Context cont, Cursor cur){
        super(cont, cur);
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_view, parent, false);
        TrackViewHolder vh = new TrackViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(TrackViewHolder viewHolder, Cursor cursor) {
        Track t = new Track();
        t.readFromCursor(cursor);
        viewHolder.title.setText(t.getTitle());
        viewHolder.artist.setText(t.getArtist());
        viewHolder.album.setText(t.getAlbum());
    }
}
