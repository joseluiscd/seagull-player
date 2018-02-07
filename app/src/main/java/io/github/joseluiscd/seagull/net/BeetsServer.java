package io.github.joseluiscd.seagull.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

import io.github.joseluiscd.seagull.model.Album;
import io.github.joseluiscd.seagull.model.AppPreferences;
import io.github.joseluiscd.seagull.model.Track;
import io.github.joseluiscd.seagull.util.Callback;
import okhttp3.OkHttpClient;

/**
 * Created by joseluis on 3/12/17.
 */

public class BeetsServer {
    private Context context;
    private String server;
    private URL serverUrl;

    @NonNull public final Tracks tracks;
    @NonNull public final Albums albums;
    @NonNull public final Artists artists;


    public static void setServerURL(Context context, String url){
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editPrefs = prefs.edit();
        editPrefs.putString(AppPreferences.SERVER_URL, url);
        editPrefs.apply();
    }

    public static String getServerURL(Context context){
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(AppPreferences.SERVER_URL, null);
    }

    public BeetsServer(Context appContext){
        this.context = appContext;


        try{
            this.server = getServerURL(appContext);
            this.serverUrl = new URL(server);
        } catch (MalformedURLException e) {
            //TODO: Potencial fallo de seguridad
            System.exit(100);
        }

        tracks = new Tracks(this);
        albums = new Albums(this);
        artists = new Artists(this);

    }

    public String getUrlWithPath(String path){
        return getUrlWithPath(path, true);
    }

    public String getUrlWithPath(String path, boolean finalSlash){
        if(!path.endsWith("/") && finalSlash){
            path = path + "/";
        }

        try {
            return new URL(serverUrl.getProtocol(), serverUrl.getHost(), serverUrl.getPort(), path).toString();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public void isOnline(Callback<Boolean> cb){
        NetworkManager.getInstance(context).checkOnline(server, cb);
    }

    public class Tracks {
        BeetsServer server;

        Tracks(BeetsServer server){
            this.server = server;
        }

        public void getAll(Callback<Track[]> cb){
            final Callback<Track[]> c = cb;

            GsonRequest<TrackItems> req = new GsonRequest<>(
                    Request.Method.GET,
                    getUrlWithPath("/item"),
                    TrackItems.class,
                    new Response.Listener<TrackItems>() {
                        @Override
                        public void onResponse(TrackItems response) {
                            c.call(response.items);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            c.call(null);
                        }
                    }

            );
            NetworkManager.getInstance(context).request(req);
        }

        public void queryTracks(String query, Callback<Track[]> cb) {
            final Callback<Track[]> c = cb;

            GsonRequest<TrackQuery> req = new GsonRequest<>(
                    Request.Method.GET,
                    getUrlWithPath("/item/query/"+query),
                    TrackQuery.class,
                    new Response.Listener<TrackQuery>() {
                        @Override
                        public void onResponse(TrackQuery response) {
                            c.call(response.results);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            c.call(null);
                        }
                    }

            );
            NetworkManager.getInstance(context).request(req);
        }

    }

    public class Albums {
        BeetsServer server;

        Albums(BeetsServer server){
            this.server = server;
        }

        public void queryAlbums(String query, final Callback<Album[]> c){
            GsonRequest<AlbumQuery> req = new GsonRequest<>(
                    Request.Method.GET,
                    getUrlWithPath("/album/query/"+query),
                    AlbumQuery.class,
                    new Response.Listener<AlbumQuery>() {
                        @Override
                        public void onResponse(AlbumQuery response) {
                            c.call(response.results);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            c.call(null);
                        }
                    }

            );
            NetworkManager.getInstance(context).request(req);
        }
    }

    public class Artists {
        BeetsServer server;

        Artists(BeetsServer server){ this.server = server; }

        public void allArtists(final Callback<String[]> c){
            GsonRequest<ArtistItems> req = new GsonRequest<>(
                    Request.Method.GET,
                    getUrlWithPath("/artist"),
                    ArtistItems.class,
                    new Response.Listener<ArtistItems>() {
                        @Override
                        public void onResponse(ArtistItems response) {
                            c.call(response.artist_names);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            c.call(null);
                        }
                    }

            );
            NetworkManager.getInstance(context).request(req);
        }
    }

    private class TrackItems {
        public Track[] items;
    }

    private class ArtistItems {
        public String artist_names[];
    }

    private class TrackQuery {
        public Track[] results;
    }

    private class AlbumQuery {
        public Album[] results;
    }

    private class AlbumWithCallback{
        public Album album;
        public Callback<AlbumWithCallback> cb;

    }
}

