package io.github.joseluiscd.seagull.model;

import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import io.github.joseluiscd.seagull.R;
import io.github.joseluiscd.seagull.db.Collection;
import io.github.joseluiscd.seagull.db.DatabaseItem;
import io.github.joseluiscd.seagull.net.BeetsServer;

/**
 * Created by joseluis on 2/12/17.
 */

public class Track implements DatabaseItem, Serializable{
    private int id;
    private int album_id = -1;
    private int year = 0;
    private String title;
    private String album;
    private String artist;

    public Track(){
    }

    public static Track fromJSON(String json){
        return new Gson().fromJson(json, Track.class);
    }

    public String toString(){
        return getTitle() + " - " + getArtist();
    }

    @Override
    public void readFromCursor(Cursor c) {
        int t = c.getColumnIndex(Collection.TRACK_TITLE);
        setTitle(c.getString(t));

        t = c.getColumnIndex(Collection.TRACK_ALBUM);
        setAlbum(c.getString(t));

        t = c.getColumnIndex(Collection.TRACK_ARTIST);
        setArtist(c.getString(t));
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbumId() {
        return album_id;
    }

    public void setAlbumId(int album_id) {
        this.album_id = album_id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl(){
        return BeetsServer.getInstance().getUrlWithPath("/item/"+id+"/file", false);
    }
}
