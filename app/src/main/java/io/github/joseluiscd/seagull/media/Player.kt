package io.github.joseluiscd.seagull.media

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import io.github.joseluiscd.seagull.model.Track
import io.github.joseluiscd.seagull.net.BeetsServer

/**
 * Created by joseluis on 4/02/18.
 */
class Player()
    : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener{

    companion object {
        const val ARG_URL: String = "Miau"
    }

    inner class PlayerBinder: Binder() {
        val service: Player
            get() = this@Player
    }

    val queue = Queue()
    val player = MediaPlayer()
    lateinit var beets: BeetsServer
    lateinit var theHandler: Handler

    var isPlaying: Boolean = false
        private set

    var onTrackPlayerListener: OnTrackPlayedListener? = null
    var onTrackUpdateListener: OnTrackUpdateListener? = null

    var playingSomething = false
        private set


    override fun onBind(intent: Intent?): IBinder? = PlayerBinder()

    fun pause(){
        player.pause()
        isPlaying = false
    }

    fun play(){
        if(playingSomething){
            player.start()
        } else {
            playingSomething = true
            prepareNext()
        }

        val r: Runnable = object : Runnable {
            override fun run() {
                if(player.isPlaying){
                    val t = player.currentPosition.toFloat()/ player.duration.toFloat()
                    onTrackUpdateListener?.trackUpdated(t)
                    theHandler.postDelayed(this, 1000)
                }
            }

        }
        theHandler.postDelayed(r, 1000)

    }

    fun next(){
        player.stop()
        player.reset()
        prepareNext()
    }

    private fun prepareNext(){
        val t = queue.popNext()
        if(t != null){
            onTrackPlayerListener?.onTrackPlayed(t)
            player.setDataSource(t.url(beets))
            player.prepareAsync()
        }
    }

    override fun onCreate() {
        super.onCreate()
        player.setOnPreparedListener(this)
        player.setOnCompletionListener(this)
        beets = BeetsServer(applicationContext)
        theHandler = Handler()
    }

    override fun onPrepared(mp: MediaPlayer) {
        play()
        isPlaying = true
    }

    override fun onCompletion(mp: MediaPlayer?) {
        isPlaying = false
        player.reset()
        prepareNext()
    }

    interface OnTrackPlayedListener {
        fun onTrackPlayed(t: Track)
    }

    interface OnTrackUpdateListener {
        fun trackUpdated(percent: Float)
    }

}