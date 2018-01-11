package io.github.joseluiscd.seagull.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.github.joseluiscd.seagull.model.Album;
import io.github.joseluiscd.seagull.model.Track;

/**
 * Created by joseluis on 8/12/17.
 */

public class Collection {
    private static Collection instance;
    private static Context context;
    private CollectionOpenHelper helper;

    public static final String ALBUM_TABLE = "albums";
    public static final String ALBUM_TITLE = "title";
    public static final String ALBUM_ARTIST = "artist";

    public static final String TRACK_TABLE = "tracks";
    public static final String TRACK_TITLE = "title";
    public static final String TRACK_ALBUM = "album";
    public static final String TRACK_ARTIST = "artist";

    private ArrayList<TrackListener> trackListeners = new ArrayList<>();

    private Collection(){
        helper = new CollectionOpenHelper(context);
    }

    public static void setContext(Context c){
        context = c;
    }

    public static Collection getInstance(){
        if(instance == null){
            instance = new Collection();
        }

        return instance;
    }

    public Cursor getAllAlbums(){
        SQLiteDatabase db = helper.getReadableDatabase();
        return db.query("albums", null, null, null, null, null, "title");
    }

    public Cursor getAllArtists(){
        SQLiteDatabase db = helper.getReadableDatabase();
        return db.query("artists", null, null, null, null, null, "sort_name");
    }

    public Cursor getAllTracks(){
        SQLiteDatabase db = helper.getReadableDatabase();
        return db.query(TRACK_TABLE, null, null, null, null, null, "title");

    }

    public Cursor queryTracks(String query){
        SQLiteDatabase db = helper.getReadableDatabase();
        return db.query(TRACK_TABLE, null, "title LIKE '%?%'", new String[]{query}, null, null, "title");
    }

    public Cursor getEmptyTracksCursor(){
        SQLiteDatabase db = helper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM tracks WHERE 1=0;", null);
    }

    public boolean trackExists(int id){
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query(TRACK_TABLE, new String[]{"_id"}, "_id=?", new String[]{Integer.toString(id)}, null, null, null, null);
        if(c.isAfterLast()){
            c.close();
            return false;
        }
        c.close();
        return true;
    }

    public void _insertTrack(Track t){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("_id", t.getId());
        v.put(TRACK_TITLE, t.getTitle());
        v.put(TRACK_ALBUM, t.getAlbum());
        v.put(TRACK_ARTIST, t.getArtist());
        db.insert(TRACK_TABLE, null, v);
    }

    public void insertTrack(Track t){
        _insertTrack(t);
        notifyTracksChanged();
    }

    private void notifyTracksChanged(){
        for(TrackListener t: trackListeners){
            t.onTrackListChanged();
        }
    }

    public void addTrackListener(TrackListener tl){
        trackListeners.add(tl);
    }

    public void removeTrackListener(TrackListener tl){
        trackListeners.remove(tl);
    }

    class CollectionOpenHelper extends SQLiteOpenHelper {
        public static final String DATABASE_NAME = "collection.db";
        public static final int DATABASE_VERSION = 1;

        public CollectionOpenHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE artists(" +
                    "name TEXT PRIMARY KEY," +
                    "sort_name TEXT UNIQUE," +
                    "mbid TEXT UNIQUE);");

            db.execSQL("CREATE INDEX artist_sort ON artists(sort_name);");

            db.execSQL("CREATE TABLE albums(" +
                    "_id INTEGER PRIMARY KEY," +
                    "title TEXT," +
                    "year INTEGER," +
                    "genre TEXT," +
                    "mbid TEXT UNIQUE," +
                    "artist REFERENCES artists);");

            db.execSQL("CREATE INDEX albums_sort ON albums(title);");

            db.execSQL("CREATE TABLE tracks(" +
                    "_id INTEGER PRIMARY KEY," +
                    "title TEXT," +
                    "artist TEXT," +
                    "year INTEGER," +
                    "genre TEXT," +
                    "mbid TEXT UNIQUE," +
                    "album INTEGER REFERENCES albums);");

            db.execSQL("CREATE INDEX artist_sort_tracks ON tracks(title);");

            db.execSQL("CREATE TABLE tags(_id INTEGER PRIMARY KEY AUTOINCREMENT, tag TEXT);");

            db.execSQL("CREATE TABLE track_tags(track INTEGER REFERENCES tracks, tag INTEGER REFERENCES tags);");
            db.execSQL("CREATE TABLE album_tags(album INTEGER REFERENCES albums, tag INTEGER REFERENCES tags);");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
