package io.github.joseluiscd.seagull;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import io.github.joseluiscd.seagull.adapters.Adapter;
import io.github.joseluiscd.seagull.adapters.ClickListener;
import io.github.joseluiscd.seagull.adapters.TrackViewHolder;
import io.github.joseluiscd.seagull.media.Player;
import io.github.joseluiscd.seagull.model.Track;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TracksFragment extends Fragment implements ClickListener, TrackContextMenuFragment.Listener {

    private OnListFragmentInteractionListener mListener;

    private Adapter<TrackViewHolder, Track> adapter;
    public RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TracksFragment() {
    }

    public void setAdapter(Adapter<TrackViewHolder, Track> a){
        a.setClickListener(this);

        this.adapter = a;
        if(recyclerView != null){
            recyclerView.setAdapter(a);
        }
    }

    public Adapter<TrackViewHolder, Track> getAdapter(){
        return adapter;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TracksFragment newInstance(int columnCount) {
        TracksFragment fragment = new TracksFragment();
        return fragment;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getParentFragment() != null){
            mListener = (OnListFragmentInteractionListener) getParentFragment();
        } if (context instanceof OnListFragmentInteractionListener) {
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

    @Override
    public void onLongClick(@NotNull View view, int position) {
        Log.d("Troll", "Miau-click");
        Track t = adapter.getItemAt(position);
        TrackContextMenuFragment f = new TrackContextMenuFragment();
        Bundle b = new Bundle();
        b.putSerializable(TrackContextMenuFragment.ARG_TRACK, t);
        f.setArguments(b);

        f.show(getChildFragmentManager(), "Miau_tag");
    }

    @Override
    public void onContextMenuItemClicked(int position) {
        Log.d("Clicked", Integer.toString(position));
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
        void onTrackLongClicked(Track item);
    }
}
