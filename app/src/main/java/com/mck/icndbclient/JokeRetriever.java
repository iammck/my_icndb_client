package com.mck.icndbclient;

/**
 * Interface for a Joke provider. I do not want to confuse
 * an Android Provider for this so going with Retriever.
 * getJoke should return null if there is an error.
 * This should be change to some exception, possibly just
 * passing the exception up.
 * Since it is only being run with a real simple interface
 * atm, I'm going to accept a pass with a joke or a fail
 * with a null return value to an AsynchTask.
 * Created by mike on 4/5/2015.
 */
public interface JokeRetriever {
    Joke getJoke(String firstName, String lastName);
}
