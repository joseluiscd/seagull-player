package io.github.joseluiscd.seagull.model

import android.database.Cursor
import android.util.Log
import android.view.View
import android.widget.TextView

import com.google.gson.Gson
import com.squareup.picasso.Picasso

import org.json.JSONException
import org.json.JSONObject

import java.io.Serializable
import java.net.MalformedURLException
import java.net.URL

import io.github.joseluiscd.seagull.R
import io.github.joseluiscd.seagull.adapters.TrackViewHolder
import io.github.joseluiscd.seagull.db.Collection
import io.github.joseluiscd.seagull.db.DatabaseItem
import io.github.joseluiscd.seagull.net.BeetsServer

/**
 * Created by joseluis on 2/12/17.
 */

class Track : DatabaseItem, Serializable {
    var id: Int = 0
    var albumId = -1
    var year = 0
    var title: String? = null
    var album: String? = null
    var artist: String? = null
    var mb_releasegroupid: String? = null

    val albumArtURL: String?
        get() {
            return if(mb_releasegroupid != null){
                Log.d("TAG", mb_releasegroupid)
                "http://coverartarchive.org/release-group/$mb_releasegroupid/front"
            } else {
                null
            }
        }

    override fun toString(): String {
        return title + " - " + artist
    }

    fun url(beets: BeetsServer): String?{
        return beets.getUrlWithPath("/item/$id/file", false)
    }


    override fun readFromCursor(c: Cursor) {
        var t = c.getColumnIndex("_id")
        id = c.getInt(t)

        t = c.getColumnIndex(Collection.TRACK_TITLE)
        title = c.getString(t)

        t = c.getColumnIndex(Collection.TRACK_ALBUM)
        album = c.getString(t)

        t = c.getColumnIndex(Collection.TRACK_ARTIST)
        artist = c.getString(t)

        t = c.getColumnIndex(Collection.TRACK_ALBUM_RELEASE_ID)
        mb_releasegroupid = c.getString(t)
    }

    companion object {
        fun fromJSON(json: String): Track {
            return Gson().fromJson(json, Track::class.java)
        }
    }
}
