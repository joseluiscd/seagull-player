package io.github.joseluiscd.seagull.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import io.github.joseluiscd.seagull.util.Callback;

/**
 * Created by joseluis on 30/11/17.
 */

public class NetworkManager {
    private RequestQueue queue;
    private static NetworkManager instance;

    private NetworkManager(Context context){
        queue = Volley.newRequestQueue(context);
    }

    public static NetworkManager getInstance(Context context){
        if(instance == null){
            instance = new NetworkManager(context);
        }

        return instance;
    }

    public RequestQueue getQueue(){
        return queue;
    }

    public void request(Request<?> r){
        queue.add(r);
    }

    public void checkOnline(String serverUrl, Callback<Boolean> cb){
        final Callback<Boolean> c = cb;

        Request<String> req = new StringRequest(Request.Method.GET, serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                c.call(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                c.call(false);
            }
        });

        queue.add(req);
    }

}
