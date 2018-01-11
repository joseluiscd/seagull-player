package io.github.joseluiscd.seagull.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;

import io.github.joseluiscd.seagull.R;
import io.github.joseluiscd.seagull.model.Album;

/**
 * Created by joseluis on 7/12/17.
 */

public class AlbumAdapters {
    public static ListAdapter albumArrayAdapter(Context context, Album albums[]){
        return new ArrayAdapter<Album>(context, android.R.layout.simple_gallery_item, albums);
    }

    public static CursorAdapter albumCursorAdapter(Context context, Cursor c){
        return new CursorAdapter(context, c, false) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.album_view, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                Album a = new Album();
                a.readFromCursor(cursor);
                a.fillLayout(view);
            }
        };
    }
}
