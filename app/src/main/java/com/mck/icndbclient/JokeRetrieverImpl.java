package com.mck.icndbclient;

import android.os.AsyncTask;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * This class is used to retrieve ICNDb jokes.
 * Created by mike on 4/3/2015.
 */
public class JokeRetrieverImpl implements JokeRetriever{

    public JokeRetrieverImpl(){}

    // public for testing.
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    /**
     * gets the joke with given name. The Uri
     * http://api.icndb.com/jokes/random?firstName=John&amp&lastName=Doe&amp&escape=javascript  ;
     * is used to retrieve a random joke with the given name
     * If the unable to return a joke then will return null.
     */
    public Joke getJoke(String firstName, String lastName){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://api.icndb.com/jokes/random?firstName=");
        stringBuilder.append(firstName);
        stringBuilder.append("&amp&lastName=");
        stringBuilder.append(lastName);
        stringBuilder.append("&amp&escape=javascript");
        String uri = stringBuilder.toString();
        try {
            RestTemplate restTemplate = getRestTemplate();
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
            return restTemplate.getForObject(uri, Joke.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            Joke result = new Joke();
            result.value = new Joke.Value();
            result.value.joke = "Unable to retrieve the Joke with RestClientException.";
            result.type = "error";
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            Joke result = new Joke();
            result.value = new Joke.Value();
            result.value.joke = "Unable to retrieve the Joke. with Exception!";
            result.type = "error";
            return result;
        }
    }

    /**
     * Get the joke with the first and last name, it reports them to the responder. This
     * method uses an asycTask and does not update the ui.
     */
    @Override
    public void getJoke(String firstName, String lastName, final JokeResponder responder) {
        // create an async task and run getJoke(). use the responder in the response.
        new AsyncTask<String, String, Joke>(){
            @Override
            protected Joke doInBackground(String... params) {
                return getJoke(params[0], params[1]);
            }

            @Override
            protected void onPostExecute(Joke joke) {
                super.onPostExecute(joke);
                responder.onJokeResponse(joke);
            }
        }.execute(firstName, lastName);
    }
}
