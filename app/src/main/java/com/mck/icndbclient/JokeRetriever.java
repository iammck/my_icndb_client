package com.mck.icndbclient;

/**
 * Interface for a Joke provider. I do not want to confuse
 * an Android Provider for this so going with Retriever.
 * getJoke should return type 'error' if there is an error.
 * Created by mike on 4/5/2015.
 */
public interface JokeRetriever {

    /** Gets the joke with first and last name and returns it.*/
    Joke getJoke(String firstName, String lastName);
    /** Gets the joke with first and last name and returns them to responder.*/
    void getJoke(String firstName, String lastName, JokeResponder responder);
}
