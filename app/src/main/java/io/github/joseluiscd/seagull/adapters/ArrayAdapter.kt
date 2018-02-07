package io.github.joseluiscd.seagull.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.joseluiscd.seagull.R

/**
 * Created by joseluis on 7/02/18.
 */

abstract class ArrayAdapter<Item, ViewHolder: AbstractViewHolder<Item>>(val array: Array<Item>? = null)
    : Adapter<ViewHolder, Item>()
{
    override fun getItemAt(pos: Int): Item {
        return array!![pos]
    }

    override fun getItemCount(): Int {
        return array?.size ?: 0
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val t = array?.get(position)
        if(t != null) viewHolder.fillItem(t)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val itemView = this.createView(parent)
        return this.createViewHolder(itemView, clickListener, menuListener)
    }

    abstract fun createViewHolder(item: View, clickListener: ClickListener?, menuListener: MenuListener?): ViewHolder
    abstract fun createView(parent: ViewGroup?): View

}
