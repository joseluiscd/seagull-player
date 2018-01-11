package io.github.joseluiscd.seagull.model;

import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

import io.github.joseluiscd.seagull.R;
import io.github.joseluiscd.seagull.db.Collection;
import io.github.joseluiscd.seagull.db.DatabaseItem;

/**
 * Created by joseluis on 2/12/17.
 */

public class Album implements DatabaseItem, LayoutItem {
    private int id;
    private String title;

    public Album(){

    }

    public Album(String title){
        this.title = title;
    }

    public int getId(){
        return id;
    }

    @Override
    public void readFromCursor(Cursor c) {
        int t = c.getColumnIndex(Collection.ALBUM_TITLE);
        title = c.getString(t);
    }

    @Override
    public void fillLayout(View layout) {
        ((TextView) layout.findViewById(R.id.album_title)).setText(title);
    }
}
