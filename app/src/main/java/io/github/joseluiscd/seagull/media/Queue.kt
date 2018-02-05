package io.github.joseluiscd.seagull.media

import io.github.joseluiscd.seagull.model.Track
import java.util.*
import kotlin.collections.ArrayList


class Queue() {
    val tracks = Collections.synchronizedList(ArrayList<Track>())

    val listeners: ArrayList<OnQueueChangedListener> = ArrayList()

    fun addChangeListener(l: OnQueueChangedListener){
        listeners.add(l)
    }

    fun removeChangeListener(l: OnQueueChangedListener){
        listeners.remove(l)
    }

    private fun notifyListeners(){
        for (k in listeners){
            k.queueChanged(this)
        }
    }
    fun queueTrack(t: Track){
        tracks.add(t)
        notifyListeners()
    }

    fun popNext(): Track? {
        if(tracks.isNotEmpty()){
            val t = tracks[0]
            tracks.removeAt(0)
            notifyListeners()
            return t
        }

        return null
    }

    interface OnQueueChangedListener {
        fun queueChanged(q: Queue)
    }

}