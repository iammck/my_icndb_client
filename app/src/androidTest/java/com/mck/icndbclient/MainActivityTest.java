package com.mck.icndbclient;

import android.app.Activity;
import android.app.Application;
import android.support.v7.app.ActionBarActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import static org.mockito.Mockito.mock;
import com.robotium.solo.Solo;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 *
 * This test is run with gradle wrapper by using .\gradle connectedAndroidTest command.
 *
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<ActionBarActivity> {
    public MainActivityTest() {
        super(ActionBarActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Set up for default passing test must mock the JokeRetrieverAsynchTask
        JokeRetrieverAsynchTask mockRetriever = mock(JokeRetrieverAsynchTask.class) ;
        // when the retriever is getJoke method is called, return an expected value.
        String expected = "Chuck Norris escaped death\'s grip with a round house to the head. Wham!\"";
    }

    /**
     * the UI consists of
     *  A title displaying the first and last name of the target.
     *  A fragment containing the
     *  A text view for output. Initially blank.
     *  A button initially labeled 'Get Joke'
     */
    public void testUISetUpAfterLaunch(){
        // TODO
        // can we get a handle on  tvOuput,  bGetJoke

        // tvOutput resource should be found under androidTest/res/values/strings.xml
        // and not main/res/values/strings.xml . The new name is default_output_text
        // with value This is test text in test folder text!
        // the button has id bGetJoke with text Get Joke
        //Solo solo= new Solo(get);


        fail("Needs to be implemented!");
    }

    /**
     * The ui should look exactly the same as After launch
     */
    public void testUIBeforeFirstButtonClickAfterRotateScreen(){
        // TODO
        fail("Needs to be implemented!");
    }

    /**
     * During joke retrieval,
     * The output should be updated with text 'fetching...'
     * The button should not be clickable.
     */
    public void testUIDuringJokeRetrieval(){
        // TODO
        fail("Needs to be implemented!");
    }

    /**
     *  When the joke is being retrieved, the
     *  screen is rotated, and the resulting screen is waiting for joke retrieval.
     *  the UI should look exactly the same as before the the rotation during joke retrieval.
     */
    public void testUIDuringJokeRetrievalAfterRotateScreen(){
        // TODO
        fail("Needs to be implemented!");
    }

    /**
     * After the joke is received,
     * The output should be updated with text
     * The button should be accessible and its
     * text should be updated to 'Next Joke'
     */
    public void testUIAfterJokeReceived(){
        // TODO
        fail("Needs to be implemented!");
    }

    /**
     *  After Rotate screen when The joke is being retrieved,
     *  The UI should still be in retrieval state.
     */
    public void testUIAfterRotateScreenDuringJokeRetrieval(){
        // TODO
        fail("Needs to be implemented!");
    }

    /**
     *  After Rotate screen when The joke is received during rotation,
     *  The UI should be updated with the joke, the button should be available,
     *  the button should read next Joke
     */
    public void testUIAfterRotateScreenAfterJokeReceivedDuringRotation(){
        // TODO
        fail("Needs to be implemented!");
    }

    /**
     *  After Rotate screen and after the joke is received,
     *  The UI should be updated with the joke, the button should be available,
     *  the button should read next Joke
     */
    public void testUIAfterRotateScreenAfterJokeReceived(){
        // TODO
        fail("Needs to be implemented!");
    }

    /**
     * The ui should look exactly the same as it did before rotate.
     */
    public void testRotateScreenAfterJokeReceived(){
        // TODO
        fail("Needs to be implemented!");
    }

}