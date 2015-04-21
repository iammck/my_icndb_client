package com.mck.icndbclient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.slider_image_1);
        Drawable background = new BitmapDrawable(getResources(),bitmap);
        tvOutput.setBackgroundDrawable(background);
        return rootView;
    }
}
