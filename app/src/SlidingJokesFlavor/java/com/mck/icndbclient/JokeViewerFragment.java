package com.mck.icndbclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mike on 4/22/2015.
 */
public class JokeViewerFragment extends Fragment {

    private ViewPager mPager;
    private JokesPagerAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sliding_jokes_fragment, container, false);
        Log.v("com.mck", "SliderJokesFragment onCreateView() has been called.");

        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        mAdapter = new JokesPagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mAdapter);
        return rootView;
    }


    public void addJoke(Joke joke) {
        mAdapter.addJoke(joke);
        // set to the last position, zero indexed translation of adapter count.
        mPager.setCurrentItem(mAdapter.getCount() - 1, true);
    }
}
