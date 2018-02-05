package io.github.joseluiscd.seagull

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import io.github.joseluiscd.seagull.adapters.TrackQueueAdapter
import io.github.joseluiscd.seagull.media.Player
import io.github.joseluiscd.seagull.media.Queue
import io.github.joseluiscd.seagull.model.Track
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
        TracksFragment.OnListFragmentInteractionListener, Player.OnTrackPlayedListener {

    lateinit var queueFragment: TracksFragment
    lateinit var button: ImageButton

    var player: Player? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_media_control, container, false)

        button = view.findViewById(R.id.play_button)
        button.setOnClickListener{ onPlayPressed() }

        return view
    }

    override fun onStart() {
        super.onStart()
        queueFragment = this.childFragmentManager.findFragmentById(R.id.queue_list) as TracksFragment
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
        current_track_title.text = t.title
        current_track_artist.text = t.artist
    }

    fun onPlayPressed() {
        val pl = player ?: return
        if(pl.player.isPlaying && pl.has_something){
            pl.pause()
            button.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        } else {
            pl.play()
            button.setImageResource(R.drawable.ic_pause_black_24dp)
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onTrackClicked(item: Track?) {

    }

    override fun onTrackLongClicked(item: Track?) {

    }

    override fun onServiceDisconnected(name: ComponentName?) {
        player?.queue?.removeChangeListener(this)
        player?.onTrackPlayerListener = null
        player = null
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        player = (service as Player.PlayerBinder).service
        player?.queue?.addChangeListener(this)
        player?.onTrackPlayerListener = this
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
