package com.mck.icndbclient;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    private JokeRetriever jokeRetriever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            String title = getResources().getString(R.string.default_last_name)
                    + getResources().getString(R.string.title_half);
            setTitle(title);
            AppFragment appFragment = new AppFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.app_frag_container, appFragment)
                    .commit();
        }
        this.jokeRetriever = new JokeRetrieverImpl();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void requestJoke(JokeResponder responder) {
        // get names from resources
        SharedPreferences preferences = getPreferences(Activity.MODE_PRIVATE);
        String firstName = getResources().getString(R.string.default_first_name);
        String lastName = getResources().getString(R.string.default_last_name);

        getJokeRetriever().getJoke(firstName, lastName, responder);
    }

    private JokeRetriever getJokeRetriever() {
        return jokeRetriever;
    }

    public void setJokeRetriever(JokeRetriever jokeRetriever){
        this.jokeRetriever = jokeRetriever;
    }

}
