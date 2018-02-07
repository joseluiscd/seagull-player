package io.github.joseluiscd.seagull.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

import io.github.joseluiscd.seagull.db.Collection;
import io.github.joseluiscd.seagull.db.DatabaseItem;
import io.github.joseluiscd.seagull.net.BeetsServer;

/**
 * Created by joseluis on 2/12/17.
 */

public class Album implements DatabaseItem, Serializable {
    private int id;

    public String album;
    public String albumartist;
    public String mb_releasegroupid;

    public int tracktotal;
    public ArrayList<Track> trackList;

    public Album(){

    }

    public int getId(){
        return id;
    }

    public String getAlbumArtURL(Context c){
        if(mb_releasegroupid != null){
            //return BeetsServer.getServerURL(c)+"/album/"+id+"/art";
            return "http://coverartarchive.org/release-group/"+mb_releasegroupid+"/front";
        }
        return null;
    }

    @Override
    public void readFromCursor(Cursor c) {
        int t = c.getColumnIndex("_id");
        id = c.getInt(t);

        t = c.getColumnIndex(Collection.ALBUM_TITLE);
        album = c.getString(t);

        t = c.getColumnIndex(Collection.ALBUM_ARTIST);
        albumartist = c.getString(t);

        t = c.getColumnIndex(Collection.ALBUM_RELEASE_ID);
        mb_releasegroupid = c.getString(t);
    }

    @Override
    public void dumpToCursor(@NonNull ContentValues values) {
        values.put("_id", id);
        values.put(Collection.ALBUM_TITLE, album);
        values.put(Collection.ALBUM_ARTIST, albumartist);
        values.put(Collection.ALBUM_RELEASE_ID, mb_releasegroupid);
    }


}
