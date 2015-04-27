package com.mck.icndbclient;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;

import com.mck.icndbclient.provider.JokeProviderContract;

/**
 * Created by mike on 4/26/2015.
 */
public class JokeDataHandler implements JokeRetriever.JokeResponder {

    private final Activity activity;
    private JokeRetriever jokeRetriever;

    public JokeDataHandler(Activity activity){
        this.activity = activity;
        jokeRetriever = new JokeRetriever();
    }

    public void setJokeRetriever(JokeRetriever jokeRetriever){
        this.jokeRetriever = jokeRetriever;
    }

    public void requestJoke(){
        // get names from resources
        SharedPreferences preferences = activity.getPreferences(Activity.MODE_PRIVATE);
        String firstName = activity.getResources().getString(R.string.default_first_name);
        String lastName = activity.getResources().getString(R.string.default_last_name);
        jokeRetriever.getJoke(firstName, lastName, this);
    }

    public void requestJoke(JokeRetriever.JokeResponder responder) {
        // get names from resources
        SharedPreferences preferences = activity.getPreferences(Activity.MODE_PRIVATE);
        String firstName = activity.getResources().getString(R.string.default_first_name);
        String lastName = activity.getResources().getString(R.string.default_last_name);

        jokeRetriever.getJoke(firstName, lastName, responder);
    }


    @Override
    public void onJokeResponse(Joke joke) {
        // create content values from the joke
        ContentValues values = new ContentValues();
        values.put(JokeProviderContract.JokesTable.type, joke.type);
        values.put(JokeProviderContract.JokesTable.joke_id, joke.value.id);
        values.put(JokeProviderContract.JokesTable.joke, joke.getJoke());
        if (joke.value.categories !=null) {
            String categories = "";
            for (String category : joke.value.categories) {
                categories += category + " ";
            } // trim? ok.
            values.put(JokeProviderContract.JokesTable.categories, categories.trim());
        }
        activity.getContentResolver().insert(JokeProviderContract.JokesTable.URI, values);
    }


}
