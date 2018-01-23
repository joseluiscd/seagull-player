package io.github.joseluiscd.seagull.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.joseluiscd.seagull.R;
import io.github.joseluiscd.seagull.model.Track;

/**
 * Created by joseluis on 11/01/18.
 */

public class TrackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private final View mView;
    TextView title;
    TextView artist;
    TextView album;
    ImageView albumArt;

    private ClickListener clickListener;

    public TrackViewHolder(View view, ClickListener clickListener) {
        super(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);

        mView = view;
        title = view.findViewById(R.id.track_title);
        artist = view.findViewById(R.id.track_artist);
        album = view.findViewById(R.id.track_album);
        albumArt = view.findViewById(R.id.track_album_image);

        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        if(clickListener != null) clickListener.onClick(mView, getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View v) {
        if(clickListener != null) clickListener.onLongClick(mView, getAdapterPosition());
        return true;
    }
}