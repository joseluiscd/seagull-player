package io.github.joseluiscd.seagull.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.joseluiscd.seagull.R

/**
 * Created by joseluis on 7/02/18.
 */

class ArtistArrayAdapter(artists: Array<String>? = null)
    : ArrayAdapter<String, AbstractViewHolder<String>>(artists)
{
    override fun createViewHolder(item: View, clickListener: ClickListener?, menuListener: MenuListener?): AbstractViewHolder<String>
        = object : AbstractViewHolder<String>(item, clickListener, menuListener){
            override fun fillItem(i: String) {
                if(i == "") {
                    (item as TextView).text = "(Unknown)"
                } else {
                    (item as TextView).text = i
                }
            }

        }

    override fun createView(parent: ViewGroup?): View
        = LayoutInflater.from(parent?.context).inflate(android.R.layout.simple_list_item_1, parent, false)


}