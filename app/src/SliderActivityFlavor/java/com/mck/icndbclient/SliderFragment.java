package com.mck.icndbclient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SliderFragment extends Fragment {

    public static final String KEY_POSITION = "SliderFragment.KEY_POSITION" ;

    public SliderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.slider_fragment,container, false);
        int id;
        id = getArguments().getInt(KEY_POSITION);
        TextView tvOutput = (TextView) rootView.findViewById(R.id.slider_output);
        tvOutput.setText("The current page id is "
                + String.valueOf(getArguments().getInt(KEY_POSITION))
                + "\n Slide your finger across the screen for another joke.");
        return rootView;
    }
}
