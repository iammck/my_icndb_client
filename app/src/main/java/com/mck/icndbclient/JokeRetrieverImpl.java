package com.mck.icndbclient;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * This class is used to retrieve ICNDb jokes.
 * Created by mike on 4/3/2015.
 */
public class JokeRetrieverImpl implements JokeRetriever{

    public JokeRetrieverImpl(){}

    /**
     * gets the joke with default values. The Uri
     * http://api.icndb.com/jokes/random
     * is used to retrieve a random joke.
     */
    /*public Joke getJoke(){
        RestTemplate restTemplate = getRestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        return restTemplate.getForObject(
                "http://api.icndb.com/jokes/random?escape=javascript", Joke.class);
    }*/

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
        RestTemplate restTemplate = getRestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        try {
            return restTemplate.getForObject(uri, Joke.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    // public for testing.
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
