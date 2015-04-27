package com.mck.icndbclient;

import android.content.pm.ActivityInfo;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.robotium.solo.Solo;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 *
 * This test is run with gradle wrapper by using .\gradle connectedAndroidTest command.
 *
 */
public class AppFragmentTest extends ActivityInstrumentationTestCase2<MainActivity> {

    // Need a default constructor to forward to super the proper class. (def by T?)
    public AppFragmentTest() {
        super(MainActivity.class);
    }

    Solo solo;
    String bGetJokeInitialString;
    String stvOutputInitialString;
    String expectedJokeString;
    Joke expectedJoke;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // instantiating Robotium. Solo instance.
        solo = new Solo(getInstrumentation(), getActivity());
        // expected strings
        expectedJokeString = "Chuck Norris escaped death\'s grip with a round house kick to the head. \"Wham!\"";
        bGetJokeInitialString = getActivity()
                .getResources().getString(R.string.get_joke);
        stvOutputInitialString = getActivity()
                .getResources().getString(R.string.default_output_text);

        // an expected joke.
        expectedJoke = new Joke();
        expectedJoke.value = new Joke.Value();
        expectedJoke.value.joke = expectedJokeString;
        expectedJoke.type = "success";
    }

    /**
     * the UI consists of
     * A title displaying the first and last name of the target.
     * A fragment containing the
     * A text view for output. Initially blank.
     * A button initially labeled 'Get Joke'
     */
    public void testUISetUpAfterLaunch() {
        // can we get a handle on  the tvOuput,  bGetJoke views?
        TextView tvOutput = solo.getText(stvOutputInitialString);
        Button bGetJoke = solo.getButton(bGetJokeInitialString);
        assertNotNull(tvOutput);
        assertNotNull(bGetJoke);
    }

    /**
     * The ui should look exactly the same as After launch
     */
    public void testUIBeforeFirstButtonClickAfterRotateScreen() {
        solo.setActivityOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TextView tvOutput1 = solo.getText(stvOutputInitialString);
        Button bGetJoke1 = solo.getButton(bGetJokeInitialString);
        assertNotNull(tvOutput1);
        assertNotNull(bGetJoke1);
        solo.setActivityOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        TextView tvOutput2 = solo.getText(stvOutputInitialString);
        Button bGetJoke2 = solo.getButton(bGetJokeInitialString);
        assertNotNull(tvOutput2);
        assertNotNull(bGetJoke2);
    }

    private class ResponseRunner implements Runnable{
        public final AppFragment appFragment;
        public final Joke expectedJoke;
        public ResponseRunner(AppFragment appFragment, Joke expectedJoke) {
            this.appFragment = appFragment;
            this.expectedJoke = expectedJoke;
        }
        public void run() {
            Log.v("com.mck.icndbclient", "test Joke: " + expectedJoke.getJoke());
            appFragment.onJokeResponse(expectedJoke);
        }
    }
    /**
     * After a JokeResponder returns the Joke result,
     * The tvOutput should be updated to the expected joke.
     */
    public void testUIAfterJokeResponderGetsResponse() throws Exception {
        // get the fragment.
        AppFragment appFragment = (AppFragment) getActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.app_frag_container);
        // create a response runner class to run response on UI thread.
        getActivity().runOnUiThread(new ResponseRunner(appFragment, expectedJoke));
        solo.getText(expectedJokeString);
    }



}