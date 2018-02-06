package io.github.joseluiscd.seagull.adapters

import android.view.ContextMenu
import android.view.View

/**
 * Created by joseluis on 6/02/18.
 */
interface MenuListener {
    fun onCreateContextMenu(menu: ContextMenu?, v: View?, pos: Int, menuInfo: ContextMenu.ContextMenuInfo?)
}