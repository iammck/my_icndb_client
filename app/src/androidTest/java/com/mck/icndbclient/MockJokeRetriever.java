package com.mck.icndbclient;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by mike on 4/8/2015.
 */
public class MockJokeRetriever extends AsyncTask<String, String, Joke > {
    @Override
    protected Joke doInBackground(String... params) {
        Log.d("com.mck.icndbclient", "doInBackground() from MockJokeRetriever.");
        Joke result = new Joke();
        result.value =  new Joke.Value();
        result.value.joke = " get a joke!";
        result.type = "success";
        return result;
    }
}
