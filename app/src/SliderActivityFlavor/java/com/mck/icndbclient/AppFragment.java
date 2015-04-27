package com.mck.icndbclient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * s
 * A placeholder fragment containing a simple view.
 */
public class AppFragment extends Fragment implements View.OnClickListener, JokeRetriever.JokeResponder, ViewPager.OnPageChangeListener {

    private String LAST_RESPONSE = "AppFragmentLastJokeResponse";
    private String lastJoke;
    private ViewPager mPager;
    private WraparoundAdapterImpl mAdapter;
    private int mLastScrollPosition;

    public AppFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            this.lastJoke = savedInstanceState.getString(LAST_RESPONSE);
        } else {
            this.lastJoke = getActivity().getResources().getString(R.string.default_output_text);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(LAST_RESPONSE,lastJoke);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.app_fragment, container, false);
        TextView tvOutput = (TextView) rootView.findViewById(R.id.tvOutput);
        tvOutput.setText(lastJoke);
        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        mAdapter = new WraparoundAdapterImpl(getActivity().getSupportFragmentManager(), mPager);
        mPager.setAdapter(mAdapter);
        mAdapter.setPageChangeListener(this);
        mLastScrollPosition = mPager.getCurrentItem();
        return rootView;
    }

    @Override
    public void onClick(View v) {
        ((MainActivity) getActivity()).requestJoke(this);
    }

    @Override
    public void onJokeResponse(Joke joke) {
        lastJoke = joke.getJoke();
        // Add the joke to the output text view.
        View v = getView();
        if (v != null) {
            TextView tvOutput = (TextView) v.findViewById(R.id.tvOutput);
            if (tvOutput != null){
                tvOutput.setText(lastJoke);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position != mLastScrollPosition && positionOffset == 0){
            mLastScrollPosition = position;
            ((MainActivity) getActivity()).requestJoke(this);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
