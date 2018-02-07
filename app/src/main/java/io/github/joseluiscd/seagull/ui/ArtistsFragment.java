package io.github.joseluiscd.seagull.ui;

import android.view.ContextMenu;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import io.github.joseluiscd.seagull.R;
import io.github.joseluiscd.seagull.adapters.AbstractViewHolder;

/**
 * Created by joseluis on 7/02/18.
 */

public class ArtistsFragment extends RecyclerFragment<String, AbstractViewHolder<String>> {
    @Override
    public void onClick(@NotNull View view, int position) {

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_artists;
    }

    @Override
    protected void onContextMenu(String t, ContextMenu m, int pos, View v) {

    }
}
