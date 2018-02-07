package io.github.joseluiscd.seagull

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import io.github.joseluiscd.seagull.model.Album
import io.github.joseluiscd.seagull.ui.AlbumsFragment
import io.github.joseluiscd.seagull.ui.ArtistsFragment

import io.github.joseluiscd.seagull.ui.TracksFragment

/**
 * Created by joseluis on 8/12/17.
 */

class CollectionPagerAdapter(c: Context, fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager){

    val context: Context = c.applicationContext

    val albumsFragment: AlbumsFragment = AlbumsFragment()
    val tracksFragment: TracksFragment = TracksFragment.newInstance(true)
    val artistsFragment: ArtistsFragment = ArtistsFragment()


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
