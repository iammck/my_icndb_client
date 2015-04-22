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
public class SlidingJokesFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private ViewPager mPager;
    private WraparoundAdapterImpl mAdapter;
    private int mLastScrollPosition;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sliding_jokes_fragment, container, false);
        Log.v("com.mck", "SliderJokesFragment onCreateView() has been called.");

        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        mAdapter = new WraparoundAdapterImpl(getChildFragmentManager(), mPager);
        mPager.setAdapter(mAdapter);
        mAdapter.setPageChangeListener(this);
        mLastScrollPosition = mPager.getCurrentItem();
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    //////
    // ViewPager.OnPageChangeListener overrides.
    //////

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
