package com.mck.icndbclient;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mck.icndbclient.provider.JokeProviderContract;

import java.util.ArrayList;

/**
 * Created by mike on 4/22/2015.
 */
public class JokeViewerFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 42;

    private static final String KEY_RESTART_POSITION =
            "JokeViewerFragment.KEY_RESTART_POSITION";

    private static final String KEY_JOKE_IDS =
            "JokeViewerFragment.KEY_JOKE_IDS";

    private ViewPager mPager;
    private JokesPagerAdapter mAdapter;

    // Using db ids and not joke ids to keep track of loaded Jokes.
    private ArrayList<Integer> jokeIds;
    private int restartPosition = -1;
    private boolean needsRestart = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            jokeIds = new ArrayList<Integer>();
        } else {
            jokeIds = savedInstanceState.getIntegerArrayList(KEY_JOKE_IDS);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Initialize the loader with this as the LoaderCallbacks impl.
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.joke_viewer_fragment, container, false);

        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        int pos = prefs.getInt(KEY_RESTART_POSITION, -1);
        Log.v("com.mck", "SliderJokesFragment onCreateView() has been called restart position is "
                + pos + ".");
        // if there is no prev state and there is a restart position
        if (savedInstanceState == null && pos >= 0){
            prefs.edit().putInt(KEY_RESTART_POSITION, -1).commit();
            restartPosition = pos;
        }
        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        mAdapter = new JokesPagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v("com.mck", "SliderJokesFragment onStop() called");
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_RESTART_POSITION, mPager.getCurrentItem()).commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList(KEY_JOKE_IDS, jokeIds);
    }

    // cursor loader callbacks for the jokes db. This Viewer will attempt to update
    // the interface when ever the db is updated.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Get all columns and and rows. in the default (asc) order.
        Loader result = new CursorLoader(getActivity(),
                JokeProviderContract.JokesTable.URI, null,null,null,null);
        return result;
    }

    /**
     * makes the assumption that the db ids are in order, starting at one,
     * incremental, and have no gaps.
     *
     * @param loader the cursor loader for the joke table uri.
     * @param cursor the cursor containing all the rows available in joke table.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d("com.mck","onLoadFinished with cursor and adapter counts are "
                + cursor.getCount() + " and " + mAdapter.getCount() + ".");
        // if cursor size is zero, need to add a fragment to the adapter with id = 1.
        cursor.moveToFirst();
        if (cursor.getCount() == 0 ){
            mAdapter.addFragment(1);
            return;
        }
        // if cursor size is equal to adapter count need to add a new fragment to the adapter with cursorsize + 1 for id.
        if (cursor.getCount() == mAdapter.getCount()){
            mAdapter.addFragment(cursor.getCount() + 1);
            return;
        }

        // if adapter count is zero, need add all the rows plus one and set pager position.
        if (mAdapter.getCount() == 0){
            ArrayList<Integer> ids = new ArrayList<Integer>();
            // if the cursor is not empty.
            if ( cursor.moveToFirst()) {
                // get the indices for the db id.
                int index = cursor.getColumnIndex(JokeProviderContract.JokesTable._ID);
                do {
                    ids.add(cursor.getInt(index));
                    // If the cursor row is not in the the adapter, add it.
                    Log.d("com.mck", "Viewer fragment adding joke id " + cursor.getInt(index) + " to list of ids.");
                    //while there is another row to process.
                } while (cursor.moveToNext());
                // now add the id + 1
                ids.add(new Integer(cursor.getCount() + 1));

            }
            mAdapter.addFragments(ids);

            // get the last position, if there is not one, set it to the last item.
            // Pager indices start with 0, but our db starts with 1
            //SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            //restartPosition = preferences.getInt(KEY_RESTART_POSITION, cursor.getCount() - 1);
            Log.v("com.mck", "restartPosition is set at " + restartPosition);
            mPager.setCurrentItem(restartPosition, false);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // not holding onto any loader resources, so no need to let go.
    }
}
