package io.github.joseluiscd.seagull.behavior

import android.support.design.widget.Snackbar
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import io.github.joseluiscd.seagull.adapters.TrackQueueAdapter
import io.github.joseluiscd.seagull.media.Queue
import io.github.joseluiscd.seagull.model.Track
import io.github.joseluiscd.seagull.ui.TracksFragment

/**
 * Created by joseluis on 7/02/18.
 */
class TrackPlaylistBehavior(val queueAdapter: TrackQueueAdapter)
    : TracksFragment.OnListFragmentInteractionListener
{

    companion object {
        const val REMOVE_TRACK = 0
    }

    override fun onTrackClicked(item: Track?, pos: Int, v: View?) {
        if(item != null){
            queueAdapter.queue.tracks.removeAt(pos)
            queueAdapter.notifyItemRemoved(pos)
            queueAdapter.queue.setNextTrack(item)
        }
    }

    override fun onTrackContextMenu(item: Track?, m: ContextMenu?, pos: Int, v: View?) {
        if(item != null && m != null){

            m.add(Menu.NONE, REMOVE_TRACK, 0, "Remove")
                    .setOnMenuItemClickListener { onTrackContextMenuItemSelected(it, pos, item) }
        }
    }

    fun onTrackContextMenuItemSelected(menuItem: MenuItem, pos: Int, track: Track): Boolean {
        when(menuItem.itemId){
            REMOVE_TRACK -> {
                queueAdapter.queue.tracks.removeAt(pos)
                queueAdapter.notifyItemRemoved(pos)
            }

            else -> return false
        }

        return true
    }

}