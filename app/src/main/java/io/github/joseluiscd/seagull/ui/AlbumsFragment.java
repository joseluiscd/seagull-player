package io.github.joseluiscd.seagull.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.github.joseluiscd.seagull.R;
import io.github.joseluiscd.seagull.adapters.Adapter;
import io.github.joseluiscd.seagull.adapters.AlbumViewHolder;
import io.github.joseluiscd.seagull.adapters.ClickListener;
import io.github.joseluiscd.seagull.adapters.MenuListener;
import io.github.joseluiscd.seagull.model.Album;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlbumsFragment.OnListFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AlbumsFragment extends RecyclerFragment<Album, AlbumViewHolder> implements ClickListener, MenuListener {

    private OnListFragmentInteractionListener mListener;

    public AlbumsFragment() {
        super();
        this.cols = 3;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(getParentFragment() != null &&
                getParentFragment() instanceof OnListFragmentInteractionListener){
            mListener = (OnListFragmentInteractionListener) getParentFragment();
        } else if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_albums;
    }

    @Override
    public void onClick(@NotNull View view, int position) {
        if(mListener != null){
            mListener.onAlbumClicked(adapter.getItemAt(position), view);
        }
    }


    @Override
    protected void onContextMenu(Album t, ContextMenu m, View v) {
        if(mListener != null){
            mListener.onAlbumContextMenu(t, m, v);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onAlbumClicked(Album album, View v);
        void onAlbumContextMenu(Album album, ContextMenu m, View v);
    }
}
