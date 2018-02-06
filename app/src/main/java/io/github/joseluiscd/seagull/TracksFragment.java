package io.github.joseluiscd.seagull;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import io.github.joseluiscd.seagull.adapters.Adapter;
import io.github.joseluiscd.seagull.adapters.ClickListener;
import io.github.joseluiscd.seagull.adapters.MenuListener;
import io.github.joseluiscd.seagull.adapters.TrackViewHolder;
import io.github.joseluiscd.seagull.media.Player;
import io.github.joseluiscd.seagull.model.Track;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TracksFragment extends Fragment implements ClickListener, MenuListener {

    private OnListFragmentInteractionListener mListener;

    private Adapter<TrackViewHolder, Track> adapter;
    public RecyclerView recyclerView;

    public static final String ARG_ADD_PLACEHOLDER_VIEW = "miausdjfaljñsdfjl, such random";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TracksFragment() {
    }

    public void setAdapter(Adapter<TrackViewHolder, Track> a){
        a.setClickListener(this);
        a.setMenuListener(this);

        this.adapter = a;
        if(recyclerView != null){
            recyclerView.setAdapter(a);
        }
    }

    public Adapter<TrackViewHolder, Track> getAdapter(){
        return adapter;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView)inflater.inflate(R.layout.fragment_track_list, container, false);

        Context context = recyclerView.getContext();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL));

        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());

        return recyclerView;
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstance){
        super.onViewCreated(v, savedInstance);
    }

    @Override
    public void onCreateContextMenu(ContextMenu m, View v, int position, ContextMenu.ContextMenuInfo i){
        mListener.onTrackContextMenu(adapter.getItemAt(position), m, v, i);
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
        mListener.onTrackClicked(adapter.getItemAt(position));
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
        void onTrackClicked(Track item);
        void onTrackContextMenu(Track item, ContextMenu m, View v, ContextMenu.ContextMenuInfo i);
    }
}
