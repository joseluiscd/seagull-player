package io.github.joseluiscd.seagull.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.joseluiscd.seagull.adapters.AbstractViewHolder;
import io.github.joseluiscd.seagull.adapters.Adapter;
import io.github.joseluiscd.seagull.adapters.ClickListener;
import io.github.joseluiscd.seagull.adapters.MenuListener;

/**
 * Created by joseluis on 7/02/18.
 */

public abstract class RecyclerFragment<Item, ViewHolder extends AbstractViewHolder<Item>>
        extends Fragment implements ClickListener, MenuListener
{

    protected Adapter<ViewHolder, Item> adapter;
    protected RecyclerView recyclerView;

    protected int cols = 1;

    public static final String ARG_ADD_PLACEHOLDER_VIEW = "miausdjfaljñsdfjl, such random";

    public void setAdapter(Adapter<ViewHolder, Item> a){
        a.setClickListener(this);
        a.setMenuListener(this);

        this.adapter = a;
        if(recyclerView != null){
            recyclerView.setAdapter(a);
        }
    }

    public Adapter<ViewHolder, Item> getAdapter(){
        return adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView)inflater.inflate(getFragmentLayout(), container, false);

        Context context = recyclerView.getContext();

        recyclerView.setLayoutManager(new GridLayoutManager(context, cols));
        recyclerView.setAdapter(adapter);

        return recyclerView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu m, View v, int position, ContextMenu.ContextMenuInfo i){
        onContextMenu(adapter.getItemAt(position), m, v);
    }

    protected abstract int getFragmentLayout();
    protected abstract void onContextMenu(Item t, ContextMenu m, View v);

}
