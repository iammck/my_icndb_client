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
        Log.v("com.mck", "SliderJokesFragment onCreateView() has been called.");

        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        int pos = prefs.getInt(KEY_RESTART_POSITION, -1);
        if (pos >= 0){
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

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // get the indices.
        int indexdbId = cursor.getColumnIndex(JokeProviderContract.JokesTable._ID);
        int indexType = cursor.getColumnIndex(JokeProviderContract.JokesTable.type);
        int indexJoke = cursor.getColumnIndex(JokeProviderContract.JokesTable.joke);
        int indexCategories = cursor.getColumnIndex(JokeProviderContract.JokesTable.categories);
        int indexJokeId = cursor.getColumnIndex(JokeProviderContract.JokesTable.joke_id);
        // if the cursor is not empty.
        if ( cursor.moveToFirst()) {
            // if we add jokes to the adapter, will want to move to the last position.
            int lastPosition = -1;
            // for each cursor row
            do {
                Integer dbId = cursor.getInt(indexdbId);
                // If the cursor row is not in the the adapter, add it.
                if (!jokeIds.contains(dbId)){
                    Log.d("com.mck", "Viewer fragment adding joke id " + dbId + " to list of ids.");
                    jokeIds.add(dbId);
                    ContentValues jokeValues = new ContentValues();
                    jokeValues.put(JokeProviderContract.JokesTable.type, cursor.getString(indexType));
                    jokeValues.put(JokeProviderContract.JokesTable.joke, cursor.getString(indexJoke));
                    jokeValues.put(JokeProviderContract.JokesTable.categories, cursor.getString(indexCategories));
                    jokeValues.put(JokeProviderContract.JokesTable.joke_id, cursor.getString(indexJokeId));
                    // the adapter returns the position of the the added joke.
                    lastPosition = mAdapter.addJoke(new Joke(jokeValues));
                }
            // while there is another row to process.
            } while (cursor.moveToNext());
            if (restartPosition >=0){
                mPager.setCurrentItem(restartPosition, true);
                restartPosition = -1;
            } else if (lastPosition >= 0){
                mPager.setCurrentItem(lastPosition, true);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // not holding onto any loader resources, so no need to let go.
    }
}
