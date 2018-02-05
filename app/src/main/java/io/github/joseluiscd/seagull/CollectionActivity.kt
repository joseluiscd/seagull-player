package io.github.joseluiscd.seagull

import android.app.SearchManager
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import io.github.joseluiscd.seagull.adapters.ClickListener
import io.github.joseluiscd.seagull.adapters.TrackArrayAdapter
import io.github.joseluiscd.seagull.adapters.TrackCursorAdapter
import io.github.joseluiscd.seagull.db.Collection
import io.github.joseluiscd.seagull.model.AppPreferences
import io.github.joseluiscd.seagull.model.Track
import io.github.joseluiscd.seagull.net.BeetsServer
import io.github.joseluiscd.seagull.util.Callback

import kotlinx.android.synthetic.main.activity_collection.*
import java.util.*

class CollectionActivity :
        AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        TracksFragment.OnListFragmentInteractionListener
{


    companion object {
        val TAG = CollectionActivity::class.qualifiedName
        const val ARG_INIT_SERVER: String = "server_was_initialized_y_eso"

        init{

            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        }
    }

    private var pagerAdapter: CollectionPagerAdapter? = null

    lateinit var mediaControlFragment: MediaControlFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        if(intent.getBooleanExtra(ARG_INIT_SERVER, false)){
            val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)

            if (prefs.getString(AppPreferences.SERVER_URL, null) != null) {
                val serverUrl = prefs.getString(AppPreferences.SERVER_URL, null)
                BeetsServer.getInstance(applicationContext, serverUrl)
            }
        }

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


        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onTrackClicked(item: Track?) {
        Log.d("miau", item.toString())
        Log.d("miau", mediaControlFragment.player.toString())
        if(item != null) mediaControlFragment.player?.queue?.queueTrack(item)
    }

    override fun onTrackLongClicked(item: Track?) {
    }

    fun loadDefaultViews(){
        val fragTracks = pagerAdapter?.tracksFragment ?: return
        val fragAlbums = pagerAdapter?.albumsFragment ?: return
        val fragArtists = pagerAdapter?.artistsFragment ?: return

        fragTracks.adapter = TrackCursorAdapter(this, Collection.getInstance().allTracks)

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
                BeetsServer.getInstance().tracks.queryTracks(query, { tracks ->
                    if(tracks != null){
                        showQueryTracks(tracks)
                    } else {
                        showQueryTracks(Collection.getInstance().queryTracks(query))
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
        Log.d(TAG, Arrays.toString(tracks))
        val frag = pagerAdapter?.tracksFragment ?: return

        frag.adapter = TrackArrayAdapter(tracks)
    }

    fun showQueryTracks(tracks: Cursor){
        val frag = pagerAdapter?.tracksFragment ?: return

        frag.adapter = TrackCursorAdapter(this, tracks)
    }



}
