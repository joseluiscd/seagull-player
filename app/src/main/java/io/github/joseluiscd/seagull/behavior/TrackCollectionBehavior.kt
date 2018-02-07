package io.github.joseluiscd.seagull.behavior

import android.support.design.widget.Snackbar
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import io.github.joseluiscd.seagull.CollectionActivity
import io.github.joseluiscd.seagull.MediaControlFragment
import io.github.joseluiscd.seagull.db.Collection
import io.github.joseluiscd.seagull.model.Track
import io.github.joseluiscd.seagull.ui.TracksFragment
import kotlinx.android.synthetic.main.activity_collection.*

/**
 * Created by joseluis on 7/02/18.
 */

class TrackCollectionBehavior(
        val mediaControlFragment: MediaControlFragment,
        val view: View
)
    : TracksFragment.OnListFragmentInteractionListener {

    companion object {
        const val MENU_REMOVE_TRACK = 1
        const val MENU_ADD_TRACK = 2
        const val MENU_NEXT_SONG = 3
    }

    private val collection = Collection.getInstance(view.context.applicationContext)

    override fun onTrackContextMenu(item: Track?, m: ContextMenu?, pos: Int, v: View?) {
        if(item != null && m != null){
            if(collection.trackExists(item.id)){
                m.add(Menu.NONE, MENU_REMOVE_TRACK, 0, "Remove from collection")
                        .setOnMenuItemClickListener { onTrackContextMenuItemSelected(it, item) }
            } else {
                m.add(Menu.NONE, MENU_ADD_TRACK, 0, "Add to collection")
                        .setOnMenuItemClickListener { onTrackContextMenuItemSelected(it, item) }
            }

            m.add(Menu.NONE, MENU_NEXT_SONG, 1, "Next track")
                    .setOnMenuItemClickListener { onTrackContextMenuItemSelected(it, item) }
        }
    }


    fun onTrackContextMenuItemSelected(menuItem: MenuItem, track: Track): Boolean {
        when(menuItem.itemId){
            MENU_REMOVE_TRACK -> {
                collection.deleteTrack(track)
            }

            MENU_ADD_TRACK -> {
                collection.insertTrack(track)
            }

            MENU_NEXT_SONG -> {
                mediaControlFragment.player?.queue?.setNextTrack(track)

                Snackbar.make(view, "Added to queue", Snackbar.LENGTH_SHORT).show()
            }

            else -> {
                return false
            }
        }

        return true
    }

    override fun onTrackClicked(item: Track?, pos: Int, v: View?) {
        if(item != null){
            mediaControlFragment.player?.queue?.queueTrack(item)
            Snackbar.make(view, "Added to queue", Snackbar.LENGTH_SHORT).show()
        }
    }

}