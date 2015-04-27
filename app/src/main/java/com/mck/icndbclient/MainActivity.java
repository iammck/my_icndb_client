package com.mck.icndbclient;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    private JokeDataHandler jokeDataHandler;

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
        // get a handler to handle joke data requests.
        this.jokeDataHandler = new JokeDataHandler(this);
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

    public void requestJoke(JokeRetriever.JokeResponder responder) {
        this.jokeDataHandler.requestJoke(responder);
    }

    public void requestJoke(){
        this.jokeDataHandler.requestJoke();
    }

    public JokeDataHandler getJokeDataHandler(){
        return this.jokeDataHandler;
    }
}
