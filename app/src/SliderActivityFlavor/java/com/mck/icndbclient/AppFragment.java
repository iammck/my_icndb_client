package com.mck.icndbclient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * s
 * A placeholder fragment containing a simple view.
 */
public class AppFragment extends Fragment implements View.OnClickListener, JokeResponder {

    private String LAST_RESPONSE = "AppFragmentLastJokeResponse";
    private String lastJoke;

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
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        TextView tvOutput = (TextView) rootView.findViewById(R.id.tvOutput);
        tvOutput.setText(lastJoke);

        // get the pager,
        // create the adapter
        // plug in the adapter
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
}
