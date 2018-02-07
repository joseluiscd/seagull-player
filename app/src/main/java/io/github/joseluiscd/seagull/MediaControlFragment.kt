package io.github.joseluiscd.seagull

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.Fragment
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.github.joseluiscd.seagull.adapters.TrackQueueAdapter
import io.github.joseluiscd.seagull.media.Player
import io.github.joseluiscd.seagull.media.Queue
import io.github.joseluiscd.seagull.model.Track
import io.github.joseluiscd.seagull.ui.TracksFragment
import kotlinx.android.synthetic.main.fragment_media_control.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MediaControlFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MediaControlFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MediaControlFragment : Fragment(), Queue.OnQueueChangedListener, ServiceConnection,
        TracksFragment.OnListFragmentInteractionListener, Player.OnTrackPlayedListener,
        Player.OnTrackUpdateListener {

    lateinit var queueFragment: TracksFragment

    var player: Player? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_media_control, container, false)

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queueFragment = childFragmentManager.findFragmentByTag("media_control") as TracksFragment

        play_button.setOnClickListener{ onPlayPressed() }
        skip_next.setOnClickListener{
            player?.next()
        }

        clear_all.setOnClickListener{
            player?.queue?.clear()
        }
    }

    override fun onStart() {
        super.onStart()
        context.bindService(Intent(context, Player::class.java), this, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        if(player != null){
            context.unbindService(this)
        }
    }

    override fun queueChanged(q: Queue) {
        queueFragment?.adapter = TrackQueueAdapter(q)
    }

    override fun onTrackPlayed(t: Track) {
        play_button.setImageResource(R.drawable.ic_pause_black_24dp)
        current_track_title.text = t.title
        current_track_artist.text = t.artist
        Picasso.with(context.applicationContext)
                .load(t.albumArtURL)
                .placeholder(R.drawable.lp)
                .into(current_track_art)
    }

    override fun trackUpdated(percent: Float) {
        progressBar2.progress = (percent * 100).toInt()
        Log.d("Miau",(percent * 100).toString())
    }

    fun onPlayPressed() {
        val pl = player ?: return
        if(pl.isPlaying && pl.playingSomething){
            pl.pause()
            play_button.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        } else {
            pl.play()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onTrackClicked(item: Track?, view: View) {
        Log.d("Miau", "El gato se ha escapado")
    }

    override fun onTrackContextMenu(item: Track?, m: ContextMenu?, v: View?) {

    }

    override fun onServiceDisconnected(name: ComponentName?) {
        player?.queue?.removeChangeListener(this)
        player?.onTrackPlayerListener = null
        player?.onTrackUpdateListener = null
        player = null
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        player = (service as Player.PlayerBinder).service
        player?.queue?.addChangeListener(this)
        player?.onTrackPlayerListener = this
        player?.onTrackUpdateListener = this
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MediaControlFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): MediaControlFragment = MediaControlFragment()

    }
}// Required empty public constructor
