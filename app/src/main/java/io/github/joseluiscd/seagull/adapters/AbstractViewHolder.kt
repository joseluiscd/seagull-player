package io.github.joseluiscd.seagull.adapters

import android.support.v7.widget.RecyclerView
import android.view.ContextMenu
import android.view.View

/**
 * Created by joseluis on 7/02/18.
 */

abstract class AbstractViewHolder<Item>(
        private val mView: View,
        private val clickListener: ClickListener? = null,
        private val menuListener: MenuListener? = null
)
    : RecyclerView.ViewHolder(mView), View.OnClickListener, View.OnCreateContextMenuListener {

    init {
        mView.setOnCreateContextMenuListener(this)
        mView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        clickListener?.onClick(mView, adapterPosition)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menuListener?.onCreateContextMenu(menu, v, adapterPosition, menuInfo)
    }

    abstract fun fillItem(item: Item)
}
