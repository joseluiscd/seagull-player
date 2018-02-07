package io.github.joseluiscd.seagull

import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.ContextMenu
import android.view.View
import io.github.joseluiscd.seagull.adapters.TrackArrayAdapter
import io.github.joseluiscd.seagull.behavior.TrackCollectionBehavior
import io.github.joseluiscd.seagull.model.Album
import io.github.joseluiscd.seagull.model.Track
import io.github.joseluiscd.seagull.net.BeetsServer
import io.github.joseluiscd.seagull.ui.TracksFragment

import kotlinx.android.synthetic.main.activity_album.*

class AlbumActivity
    : AppCompatActivity() , TracksFragment.OnListFragmentInteractionListener

{
    companion object {
        const val ARG_ALBUM = "this is the album trolololo"
    }

    lateinit var server: BeetsServer
    lateinit var fragment: TracksFragment
    lateinit var mediaControlFragment: MediaControlFragment
    lateinit var trackSelectBehavior: TrackCollectionBehavior

    var album: Album? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val i = intent

        album = (i.getSerializableExtra(ARG_ALBUM)) as Album?

        server = BeetsServer(applicationContext)

        title = album?.album ?: "Troll-activity"

        fab.setOnClickListener { view ->

        }
    }

    override fun onStart() {
        super.onStart()
        fragment = supportFragmentManager.findFragmentByTag("album_things") as TracksFragment
        mediaControlFragment = supportFragmentManager.findFragmentByTag("media_control") as MediaControlFragment
        trackSelectBehavior = TrackCollectionBehavior(mediaControlFragment, main_album_activity)
        val album = this.album

        if(album != null){
            server.tracks.queryTracks("album_id:${album?.id}", { tracks ->
                fragment.adapter = TrackArrayAdapter(tracks)
                Log.d("Miau", "troltech")
            })
        }
    }

    override fun onTrackClicked(item: Track?, pos: Int, v: View?) = trackSelectBehavior.onTrackClicked(item, pos, v)

    override fun onTrackContextMenu(item: Track?, m: ContextMenu?, pos: Int, v: View?)
        = trackSelectBehavior.onTrackContextMenu(item, m, pos, v)

}
