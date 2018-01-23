package io.github.joseluiscd.seagull.adapters

import android.support.v7.widget.RecyclerView
import io.github.joseluiscd.seagull.model.Track

/**
 * Created by joseluis on 12/01/18.
 */
abstract class Adapter<T: RecyclerView.ViewHolder, Item>: RecyclerView.Adapter<T>() {
    var clickListener: ClickListener? = null

    abstract fun getItemAt(pos: Int): Item
}