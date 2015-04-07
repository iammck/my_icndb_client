package com.mck.icndbclient;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

/**
 * Uses the JokeRetrieverImple to getThe joke from ICNDb.com .
 * Created by mike on 4/5/2015.
 */
public class JokeRetrieverAsynchTask extends AsyncTask<String,String,Joke> {

    Activity activity;
    public JokeRetrieverAsynchTask(Activity activity) {
        this.activity = activity;
    }

    /**
     * Expecting a first and last name params String Array
     * @param params a first and last name String Array
     * @return The acquired joke or null.
     */
    @Override
    protected Joke doInBackground(String... params) {
        // get the thinking string and call publishProgress it on a separate thread.
        new Runnable() {
            @Override
            public void run() {
                String[] values = new String[3];
                values[0] = getActivity().getResources().getString(R.string.thinking1);
                values[1] = getActivity().getResources().getString(R.string.thinking2);
                values[2] = getActivity().getResources().getString(R.string.thinking3);
                int count = 0;
                while(!Thread.currentThread().isInterrupted()){
                    try {
                        Thread.sleep(4200);
                        publishProgress(values[count]);
                        count %= 3;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }.run();
        JokeRetriever jokeRetriever = new JokeRetrieverImpl();
        return jokeRetriever.getJoke(params[0],params[1]);
    }

    /**
     * Updates the tvOutput with the current output string found at
     * values[0] .
     * @param values first index has the
     */
    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        TextView tvOutput = ((TextView) getActivity().findViewById(R.id.tvOutput));
        tvOutput.setText(values[0]);
    }

    @Override
    protected void onPostExecute(Joke joke) {
        super.onPostExecute(joke);
        TextView tvOutput = ((TextView) getActivity().findViewById(R.id.tvOutput));
        String jv = joke.getJoke();
        if (jv == null)
            jv = getActivity().getResources().getString(R.string.internet_problems);
        tvOutput.setText(jv);
    }

    private Activity getActivity(){
        return activity;
    }
}