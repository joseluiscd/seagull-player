package io.github.joseluiscd.seagull.media

import android.app.Service
import android.content.Intent
import android.media.AsyncPlayer
import android.media.MediaPlayer
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserServiceCompat
import io.github.joseluiscd.seagull.model.Track

/**
 * Created by joseluis on 4/02/18.
 */
class Player: Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener{
    companion object {
        const val ARG_URL: String = "Miau"
    }

    inner class PlayerBinder: Binder() {
        val service: Player
            get() = this@Player
    }

    val queue = Queue()
    val player = MediaPlayer()

    var onTrackPlayerListener: OnTrackPlayedListener? = null

    var has_something = false

    override fun onBind(intent: Intent?): IBinder? = PlayerBinder()

    fun pause(){
        player.pause()
    }

    fun play(){
        if(has_something){
            player.start()
        } else {
            has_something = true
            prepareNext()
        }
    }

    fun next(){
        player.stop()
        prepareNext()
    }

    private fun prepareNext(){
        val t = queue.popNext()
        if(t != null){
            onTrackPlayerListener?.onTrackPlayed(t)
            player.setDataSource(t.url)
            player.prepareAsync()
        }
    }

    override fun onCreate() {
        super.onCreate()
        player.setOnPreparedListener(this)
        player.setOnCompletionListener(this)
    }

    override fun onPrepared(mp: MediaPlayer) {
        play()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        player.reset()
        prepareNext()
    }

    interface OnTrackPlayedListener {
        fun onTrackPlayed(t: Track)
    }

}