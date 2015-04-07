package com.mck.icndbclient;

import com.google.gson.Gson;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;

// this test may be build using .\gradle check --continue
// or .\gradle test --continue.

public class JokeTest extends TestCase {

    private Joke validJokeInstance;
    private String validJokeJson;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // set up a valid Joke instance
        validJokeInstance = new Joke();
        validJokeInstance.type = "success";
        validJokeInstance.value = new Joke.Value();
        validJokeInstance.value.id = 553;
        validJokeInstance.value.joke = "China lets Chuck Norris search for porn on Google.";
        validJokeInstance.value.categories = new String[1];
        validJokeInstance.value.categories[0] = "explicit";

        // set up a valid Joke json structure
        StringBuilder builder = new StringBuilder();
        builder.append( "{ \"type\": \"success\"," );
        builder.append( " \"value\": { \"id\": 553," );
        builder.append( " \"joke\": \"China lets Chuck Norris search for porn on Google.\", " );
        builder.append( " \"categories\": [\"explicit\"] } }" );
        validJokeJson = builder.toString();
    }

     public void testCreateInstanceFromJson(){
         Gson gson = new Gson();
         Joke result = gson.fromJson(validJokeJson, Joke.class);
         assertEquals(validJokeInstance.type,result.type);
         assertEquals(validJokeInstance.value.id,result.value.id);
         assertEquals(validJokeInstance.value.joke,result.value.joke);
         assertEquals(validJokeInstance.value.categories[0], result.value.categories[0]);
    }

    public void testCreateJsonStructureFromInstance(){
        try {
            Gson gson = new Gson();
            JSONObject result = new JSONObject(gson.toJson(validJokeInstance));
            JSONObject expected = new JSONObject(validJokeJson);
            assertNotNull(result);
            assertNotNull(expected);
            JSONAssert.assertEquals(expected, result, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void testGetJoke(){
        Gson gson = new Gson();
        Joke result = gson.fromJson(validJokeJson, Joke.class);
        assertEquals("China lets Chuck Norris search for porn on Google.",
                result.getJoke());
    }
}