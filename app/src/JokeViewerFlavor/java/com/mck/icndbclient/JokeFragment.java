package com.mck.icndbclient;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mck.icndbclient.provider.JokeProviderContract;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


public class JokeFragment extends Fragment {

    public static final String KEY_JOKE_ID = "JokeFragment.KEY_JOKE_ID";
    private static final String KEY_ERROR = "error";

    private Integer id;

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
        id = getArguments().getInt(KEY_JOKE_ID);
        Log.v("com.mck", "JokeFragment onCreate() with id " + id);

        Cursor cursor = getActivity().getContentResolver().query(
                JokeProviderContract.JokesTable.getItemUri(id),null,null,null,null);
        // if there is a cursor, get the joke text. and set the tv output.
        if (cursor.moveToFirst()){
            int index = cursor.getColumnIndex(JokeProviderContract.JokesTable.joke);
            String jokeText = cursor.getString(index);
            tvOutput.setText(jokeText);
        } else {

            // get joke joke in async task then update ui and db, using names from resources.
            new GetJokeAsyncTask(getActivity().getContentResolver()).execute(
                    getResources().getString(R.string.default_first_name),
                    getResources().getString(R.string.default_last_name));
        }
        cursor.close();
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.slider_image_1);
        //Drawable background = new BitmapDrawable(getResources(),bitmap);
        //tvOutput.setBackgroundDrawable(background);
        return rootView;
    }

    /**
     * gets the joke with given name. The Uri
     * http://api.icndb.com/jokes/random?firstName=John&amp&lastName=Doe&amp&escape=javascript  ;
     * is used to retrieve a random joke with the given name
     * If the unable to return a joke then will return null.
     */
    private Joke getJoke(String firstName, String lastName){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://api.icndb.com/jokes/random?firstName=");
        stringBuilder.append(firstName);
        stringBuilder.append("&amp&lastName=");
        stringBuilder.append(lastName);
        stringBuilder.append("&amp&escape=javascript");
        String uri = stringBuilder.toString();
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
            return restTemplate.getForObject(uri, Joke.class);
        } catch (RestClientException e) {
            //e.printStackTrace();
            Joke result = new Joke();
            result.value = new Joke.Value();
            result.value.joke = "Unable to retrieve the Joke with RestClientException.";
            result.type = KEY_ERROR;
            return result;
        } catch (Exception e) {
            //e.printStackTrace();
            Joke result = new Joke();
            result.value = new Joke.Value();
            result.value.joke = "Unable to retrieve the Joke. with Exception!";
            result.type = KEY_ERROR;
            return result;
        }
    }

    private class GetJokeAsyncTask extends AsyncTask <String,Integer,Joke>{

        private final ContentResolver resolver;

        public GetJokeAsyncTask(ContentResolver resolver){
            this.resolver = resolver;
        }

        @Override
        protected Joke doInBackground(String... params) {
            String firstName = params[0];
            String lastName = params[1];
            Joke result = null;

            // try to get the joke four times before accepting error result.
            for(int index = 0; index < 4; index ++) {
                result = getJoke(firstName, lastName);
                if (result.type == KEY_ERROR){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }


            // return the result
            return result;
        }

        @Override
        protected void onPostExecute(Joke joke) {
            // add result to the db using the uri and values.
            Uri uri = JokeProviderContract.JokesTable.URI;
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
            FragmentActivity activity = getActivity();
            resolver.insert(uri, values);


            // get the tvOutput View and stuff it with the joke
            View fragmentView = getView();
            if (fragmentView != null){
                TextView tvOutput = (TextView) fragmentView.findViewById(R.id.joke_fragment_output);
                tvOutput.setText(joke.getJoke());
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
