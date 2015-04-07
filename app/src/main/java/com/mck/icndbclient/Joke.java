package com.mck.icndbclient;

/**
 * Created by mike on 4/1/2015.
 *
 * The container for the json result returned from http://api.icndb.com a typical result looks like
 *
 * { "type": "success", "value": { "id": 553, "joke": "China lets Chuck Norris search for porn on Google.",
 * "categories": ["explicit"] } }
 *
 * Notice value is an inner class. Instantiating an inner class requires a reference to the outer object.
 * Since the outer object is not available at deserialization, gson can not complete the request. Following
 * https://sites.google.com/site/gson/gson-user-guide#TOC-Nested-Classes-including-Inner-Classes-
 * The inner class must be declared as static.
 *
 * GSon also requires default constructors.
 *
 */
public class Joke {
    public Joke(){

    }
    public String type;
    public Value value;

    public String getJoke(){
        return value.joke;
    }

    public static class Value {
        int id;
        String joke;
        String categories[];
    }

}
