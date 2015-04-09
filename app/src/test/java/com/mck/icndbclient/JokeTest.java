package com.mck.icndbclient;

import com.mck.icndbclient.Joke;

import junit.framework.TestCase;
public class JokeTest extends TestCase {

        // just to see where these would go.
        public void testJokeUnitTest(){
              Joke joke = new Joke();
              assertNotNull("Passed joke instantiated a unit test.", joke);
        }

        // looking for test reports..
        public void testReports(){
            assertNull("Failed joke instantiated a unit test.", null);
        }

}