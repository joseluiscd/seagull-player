package io.github.joseluiscd.seagull;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import io.github.joseluiscd.seagull.adapters.TrackCursorAdapter;
import io.github.joseluiscd.seagull.db.Collection;
import io.github.joseluiscd.seagull.db.TrackListener;
import io.github.joseluiscd.seagull.model.Track;

/**
 * Created by joseluis on 8/12/17.
 */

public class CollectionPagerAdapter extends FragmentPagerAdapter implements TrackListener{
    private FragmentManager fragmentManager;
    private Context context;

    private static String tabTitles[] = {
        "Artists",
        "Albums",
        "Tracks",
    };

    private ArrayList<Fragment> fragments = new ArrayList<>();

    public CollectionPagerAdapter(Context c, FragmentManager fm) {
        super(fm);
        context = c.getApplicationContext();
        fragmentManager = fm;

        Collection.getInstance().addTrackListener(this);

        fragments.add(new Fragment());

        fragments.add(new AlbumsFragment());

        TracksFragment tracksFragment = new TracksFragment();
        tracksFragment.setAdapter(new TrackCursorAdapter(context, Collection.getInstance().getAllTracks()));
        fragments.add(tracksFragment);
    }

    @Override
    @NonNull
    public Fragment getItem(int position) {
        if(0 <= position && position < fragments.size()){
            return fragments.get(position);
        } else {
            return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    @Override
    public void onTrackListChanged() {
    }
}
