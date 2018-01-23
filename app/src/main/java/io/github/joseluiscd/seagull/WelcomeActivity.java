package io.github.joseluiscd.seagull;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import io.github.joseluiscd.seagull.db.Collection;
import io.github.joseluiscd.seagull.model.AppPreferences;
import io.github.joseluiscd.seagull.model.Track;
import io.github.joseluiscd.seagull.net.BeetsServer;
import io.github.joseluiscd.seagull.net.NetworkManager;
import io.github.joseluiscd.seagull.util.Callback;

public class WelcomeActivity extends AppCompatActivity {

    EditText server;
    String serverUrl;
    Context appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appContext = getApplicationContext();
        Collection.setContext(appContext);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(appContext);

        if(prefs.getString(AppPreferences.SERVER_URL, null) != null){
            serverUrl = prefs.getString(AppPreferences.SERVER_URL, null);
            goToCollection();
        }

        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        server = (EditText) findViewById(R.id.server_url);

        final CollapsingToolbarLayout collapsing = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsing.setTitle(" ");

        /* Código copiado de STACKOVERFLOW
        * https://stackoverflow.com/questions/31662416/show-collapsingtoolbarlayout-title-only-when-collapsed
        * */
        final AppBarLayout appbar = (AppBarLayout) findViewById(R.id.app_bar);

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsing.setTitle(getResources().getString(R.string.app_name));
                    isShow = true;
                } else if(isShow) {
                    collapsing.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        EditText et = (EditText) findViewById(R.id.server_url);
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appbar.setExpanded(false);
            }
        });

        final Callback<Boolean> cb = new Callback<Boolean>() {
            @Override
            public void call(Boolean online) {
                if(online){
                    SharedPreferences.Editor editPrefs = prefs.edit();
                    editPrefs.putString(AppPreferences.SERVER_URL, serverUrl);
                    editPrefs.commit();

                    goToCollection();

                } else {
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(WelcomeActivity.this);
                    dlgAlert.setMessage("Could not connect to server.");
                    dlgAlert.setTitle("Error");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.create().show();
                }
            }
        };

        Button bt = (Button) findViewById(R.id.button);

        final Switch sw = (Switch) findViewById(R.id.uses_https);
        final EditText port = (EditText) findViewById(R.id.server_port);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String protocol = sw.isChecked() ? "https" : "http";
                try{
                    URL url = new URL(protocol, server.getText().toString(), Integer.parseInt(port.getText().toString()), "/");
                    serverUrl = url.toString();
                    NetworkManager.getInstance(appContext).checkOnline(serverUrl, cb);
                } catch (MalformedURLException e) {
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(WelcomeActivity.this);
                    dlgAlert.setMessage("URL is not correct");
                    dlgAlert.setTitle("Error");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.create().show();
                }
            }
        });
    }

    void goToCollection(){
        Intent t = new Intent(WelcomeActivity.this, CollectionActivity.class);
        BeetsServer.getInstance(appContext, serverUrl);
        t.putExtra(CollectionActivity.ARG_INIT_SERVER, true);

        WelcomeActivity.this.startActivity(t);
    }


}
