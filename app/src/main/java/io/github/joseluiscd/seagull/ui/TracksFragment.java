package io.github.joseluiscd.seagull.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import io.github.joseluiscd.seagull.R;
import io.github.joseluiscd.seagull.adapters.Adapter;
import io.github.joseluiscd.seagull.adapters.ClickListener;
import io.github.joseluiscd.seagull.adapters.MenuListener;
import io.github.joseluiscd.seagull.adapters.TrackViewHolder;
import io.github.joseluiscd.seagull.model.Track;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TracksFragment
        extends RecyclerFragment<Track, TrackViewHolder>
        implements ClickListener, MenuListener
{


    protected TracksFragment.OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TracksFragment() {
        super();
    }


    @SuppressWarnings("unused")
    public static TracksFragment newInstance(boolean putPlaceholder) {
        TracksFragment fragment = new TracksFragment();
        Bundle b = new Bundle();
        b.putBoolean(ARG_ADD_PLACEHOLDER_VIEW, putPlaceholder);
        fragment.setArguments(b);
        return fragment;
    }

    @SuppressWarnings("unused")
    public static TracksFragment newInstance() {
        return newInstance(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL));
        return v;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_track_list;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getParentFragment() != null){
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
    public void onClick(@NotNull View view, int position) {
        mListener.onTrackClicked(adapter.getItemAt(position), view);
    }

    @Override
    protected void onContextMenu(Track t, ContextMenu m, View v) {
        if(mListener != null){
            mListener.onTrackContextMenu(t, m, v);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTrackClicked(Track item, View v);
        void onTrackContextMenu(Track item, ContextMenu m, View v);
    }
}
