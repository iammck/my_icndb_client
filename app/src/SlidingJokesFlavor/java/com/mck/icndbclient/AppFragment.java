package com.mck.icndbclient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * s
 * A placeholder fragment containing a simple view.
 */
public class AppFragment extends Fragment implements View.OnClickListener, JokeResponder {

    private JokeViewerFragment jokeViewerFragment;

    public AppFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.app_fragment, container, false);
        Button button = (Button) rootView.findViewById(R.id.bGetJoke);
        button.setOnClickListener(this);
        if (savedInstanceState == null) {
            Log.v("com.mck", "AppFragment instantiating SliderJokesFragment.");
            // this view should contain the sliding joke fragment.
            jokeViewerFragment = new JokeViewerFragment();
            getChildFragmentManager()
                    .beginTransaction()
                    .add(R.id.sliding_jokes_frag_container, jokeViewerFragment)
                    .commit();
        }
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Joke joke = null;
        ((MainActivity) getActivity()).requestJoke(this);

    }

    @Override
    public void onJokeResponse(Joke joke) {
        jokeViewerFragment.addJoke(joke);
    }
}
