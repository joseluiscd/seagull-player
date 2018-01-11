package io.github.joseluiscd.seagull

import android.content.Context
import android.database.Cursor
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import io.github.joseluiscd.seagull.adapters.TrackArrayAdapter

import java.util.ArrayList

import io.github.joseluiscd.seagull.adapters.TrackCursorAdapter
import io.github.joseluiscd.seagull.db.Collection
import io.github.joseluiscd.seagull.db.TrackListener
import io.github.joseluiscd.seagull.model.Track

/**
 * Created by joseluis on 8/12/17.
 */

class CollectionPagerAdapter(c: Context, fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager){

    val context: Context = c.applicationContext

    val albumsFragment: Fragment = Fragment()
    val tracksFragment: TracksFragment = TracksFragment()
    val artistsFragment: Fragment = Fragment()


    override fun getItem(position: Int): Fragment = when(position){
        0 -> artistsFragment
        1 -> albumsFragment
        2 -> tracksFragment
        else -> Fragment()
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence = when(position){
        0 -> "Artists"
        1 -> "Albums"
        2 -> "Tracks"
        else -> "Troll-Tab"
    }
}
