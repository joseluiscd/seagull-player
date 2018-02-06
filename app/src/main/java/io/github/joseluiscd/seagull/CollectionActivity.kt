package io.github.joseluiscd.seagull

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import io.github.joseluiscd.seagull.adapters.TrackArrayAdapter
import io.github.joseluiscd.seagull.adapters.TrackCursorAdapter
import io.github.joseluiscd.seagull.db.Collection
import io.github.joseluiscd.seagull.model.Track
import io.github.joseluiscd.seagull.net.BeetsServer

import kotlinx.android.synthetic.main.activity_collection.*
import java.util.*

class CollectionActivity :
        AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        TracksFragment.OnListFragmentInteractionListener,
        Collection.TrackListener
{


    companion object {
        val TAG = CollectionActivity::class.qualifiedName
        const val ARG_INIT_SERVER: String = "server_was_initialized_y_eso"
        const val MENU_REMOVE_TRACK = 1
        const val MENU_ADD_TRACK = 2
        const val MENU_NEXT_SONG = 3

        init{
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    lateinit var pagerAdapter: CollectionPagerAdapter

    lateinit var mediaControlFragment: MediaControlFragment
    lateinit var beetsServer: BeetsServer

    lateinit var collection: Collection

    var search_results = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        beetsServer = BeetsServer(applicationContext)

        val toolbar = findViewById(R.id.collection_toolbar) as Toolbar
        setSupportActionBar(toolbar)


        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        pagerAdapter = CollectionPagerAdapter(this, supportFragmentManager)
        collection_pager.adapter = pagerAdapter

        collection_tabs.setupWithViewPager(collection_pager)

        collection = Collection.getInstance(applicationContext)
        collection.addTrackListener(this)

        loadDefaultViews()

    }

    override fun onStart() {
        super.onStart()
        mediaControlFragment = supportFragmentManager.findFragmentByTag("media_control") as MediaControlFragment
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.collection, menu)
        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView

        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return if(query != null) onQuery(query) else false
            }
            override fun onQueryTextChange(newText: String?): Boolean = false
        })

        searchView.setOnCloseListener {
            loadDefaultViews()
            false
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId



        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        when(id){
            R.id.item_settings -> {
                val i = Intent(this, SettingsActivity::class.java)
                startActivity(i)
            }
        }
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun onTrackMenuItemSelected(menuItem: MenuItem, track: Track): Boolean{
        when(menuItem.itemId){
            MENU_REMOVE_TRACK -> {
                collection.deleteTrack(track)
            }

            MENU_ADD_TRACK -> {
                collection.insertTrack(track)
            }

            MENU_NEXT_SONG -> {
                mediaControlFragment.player?.queue?.setNextTrack(track)
            }

            else -> {
                return false
            }
        }

        return true
    }

    override fun onTrackClicked(item: Track?) {
        Log.d("miau", item.toString())
        Log.d("miau", mediaControlFragment.player.toString())
        if(item != null){
            mediaControlFragment.player?.queue?.queueTrack(item)
        }
    }

    override fun onTrackContextMenu(item: Track?, m: ContextMenu?, v: View?, i: ContextMenu.ContextMenuInfo?) {
        if(item != null && m != null){
            if(collection.trackExists(item.id)){
                m.add(Menu.NONE, MENU_REMOVE_TRACK, 0, "Remove from collection")
                        .setOnMenuItemClickListener { onTrackMenuItemSelected(it, item) }
            } else {
                m.add(Menu.NONE, MENU_ADD_TRACK, 0, "Add to collection")
                        .setOnMenuItemClickListener { onTrackMenuItemSelected(it, item) }
            }

            m.add(Menu.NONE, MENU_NEXT_SONG, 1, "Next track")
                    .setOnMenuItemClickListener { onTrackMenuItemSelected(it, item) }
        }
    }

    fun loadDefaultViews(){
        search_results = false
        onTrackListChanged()

    }

    override fun onTrackListChanged() {
        if(!search_results){
            pagerAdapter.tracksFragment.adapter = TrackCursorAdapter(this, collection.allTracks)
        }

    }

    fun onQuery(query: String): Boolean {
        return when(collection_tabs.selectedTabPosition){
            0 -> { //Artists
                Log.d(TAG, "Artist query: $query")
                true
            }
            1 -> { //Albums
                Log.d(TAG, "Albums query: $query")
                true
            }
            2 -> { //Tracks
                Log.d(TAG, "Tracks query: $query")
                beetsServer.tracks.queryTracks(query, { tracks ->
                    if(tracks != null){
                        showQueryTracks(tracks)
                    } else {
                        showQueryTracks(collection.queryTracks(query))
                    }
                })
                true
            }
            else -> {
                false
            }
        }
    }

    fun showQueryTracks(tracks: Array<Track>){
        pagerAdapter.tracksFragment.adapter = TrackArrayAdapter(tracks)
        search_results = true
    }

    fun showQueryTracks(tracks: Cursor){
        val frag = pagerAdapter.tracksFragment

        frag.adapter = TrackCursorAdapter(this, tracks)
    }



}
