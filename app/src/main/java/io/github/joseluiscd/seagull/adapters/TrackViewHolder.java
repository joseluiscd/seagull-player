package io.github.joseluiscd.seagull.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.joseluiscd.seagull.R;

/**
 * Created by joseluis on 11/01/18.
 */

public class TrackViewHolder extends RecyclerView.ViewHolder {
    final View mView;
    TextView title;
    TextView artist;
    TextView album;
    ImageView albumArt;

    public TrackViewHolder(View view) {
        super(view);
        mView = view;
        title = view.findViewById(R.id.track_title);
        artist = view.findViewById(R.id.track_artist);
        album = view.findViewById(R.id.track_album);
        albumArt = view.findViewById(R.id.track_album_image);

    }

    @Override
    public String toString() {
        return super.toString();
    }
}