package com.mck.icndbclient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class JokeFragment extends Fragment {

    public static final String KEY_JOKE = "JokeFragment.KEY_JOKE";

    public JokeFragment() {
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
        TextView tvOutput = (TextView) rootView.findViewById(R.id.joke_fragment_output);
        Joke joke = getArguments().getParcelable(KEY_JOKE);
        tvOutput.setText(joke.getJoke());
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.slider_image_1);
        //Drawable background = new BitmapDrawable(getResources(),bitmap);
        //tvOutput.setBackgroundDrawable(background);
        return rootView;
    }
}
