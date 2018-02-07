package io.github.joseluiscd.seagull

import android.app.Application
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import io.github.joseluiscd.seagull.net.BeetsServer
import okhttp3.OkHttpClient
import okhttp3.Protocol

/**
 * Created by joseluis on 6/02/18.
 */
class Seagull: Application(){

    lateinit var beets: BeetsServer

    override fun onCreate() {
        super.onCreate()

        val picasso = Picasso.Builder(this)
                .downloader(OkHttp3Downloader(this, 52_428_800L))
                .build()

        Picasso.setSingletonInstance(picasso)

        beets = BeetsServer(this)
    }

}