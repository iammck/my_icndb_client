package com.mck.icndbclient;

import android.database.Cursor;
import android.test.ActivityInstrumentationTestCase2;

import com.mck.icndbclient.provider.JokeProviderContract;

/**
 * Created by mike on 4/25/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Joke expectedJoke;

    public MainActivityTest() {
        super(MainActivity.class);

        expectedJoke = new Joke();
        String expectedJokeString = "Chuck Norris escaped death\'s grip with a round house kick to the head. \"Wham!\"";
        // an expected joke.
        expectedJoke = new Joke();
        expectedJoke.value = new Joke.Value();
        expectedJoke.value.joke = expectedJokeString;
        expectedJoke.type = "success";
        // Are there categories? I am unsure if there are categories at least once.
    }

    /**
     * MainActivity should be able to put the results of a joke response in the db.
     * This method calls the the activities method with a stubbed Joke then checks
     * via query on the db for the result.
     */
    public void testOnJokeResponseGeneratesQuery(){
        getActivity().onJokeResponse(expectedJoke);
        // omg! o hope I don't need to set up a custom provider stuffs for this!
        // I may need to wait a sec to for the db to read.
        Cursor rCursor = getActivity()
                .getContentResolver()
                .query(JokeProviderContract.JokesTable.URI, null, null, null, null);
        assertTrue("The resulting joke response cursor could not move to the first element.", rCursor.moveToFirst());
        assertTrue("The cursor returned the wrong size.", rCursor.getCount() == 1 );
        int jokeIndex = rCursor.getColumnIndex(JokeProviderContract.JokesTable.joke);
        int typeIndex = rCursor.getColumnIndex(JokeProviderContract.JokesTable.type);
        String resultJoke = rCursor.getString(jokeIndex);
        String resultType = rCursor.getString(typeIndex);
        assertEquals("Expected joke did not equal the resulting joke.", expectedJoke.getJoke(), resultJoke);
        assertEquals("Expected Joke.type did not equal result type.", expectedJoke.type, resultType);
        // always close the cursor.
        rCursor.close();
    }
}
