package io.github.joseluiscd.seagull.net;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.net.MalformedURLException;
import java.net.URL;

import io.github.joseluiscd.seagull.model.Track;
import io.github.joseluiscd.seagull.util.Callback;

/**
 * Created by joseluis on 3/12/17.
 */

public class BeetsServer {
    private Context context;
    private String server;
    private URL serverUrl;

    @NonNull public final Tracks tracks;
    @NonNull public final Albums albums;

    private static BeetsServer instance;

    public static BeetsServer getInstance(Context appContext, String serverUrl){
        if(instance == null){
            instance = new BeetsServer(appContext, serverUrl);
        }
        return instance;
    }

    @NonNull public static BeetsServer getInstance(){
        if(instance == null){
            throw new IllegalStateException("Illo, estás loco?? Llama primero a getInstance con argumentos");
        }
        return instance;
    }

    private BeetsServer(Context appContext, String server){
        this.context = appContext;
        this.server = server;

        try{
            this.serverUrl = new URL(server);
        } catch (MalformedURLException e) {
            //TODO: Potencial fallo de seguridad
            System.exit(100);
        }

        tracks = new Tracks(this);
        albums = new Albums(this);

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

    }

    private class TrackItems {
        public Track[] items;
    }

    private class TrackQuery {
        public Track[] results;
    }
}
