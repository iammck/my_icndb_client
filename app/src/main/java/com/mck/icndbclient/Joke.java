package com.mck.icndbclient;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.mck.icndbclient.provider.JokeProviderContract;

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
public class Joke implements Parcelable{
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

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeInt(value.id);
        dest.writeString(value.joke);
        dest.writeStringArray(value.categories);

    }

    public static final Creator<Joke> CREATOR = new Creator<Joke>() {
        @Override
        public Joke createFromParcel(Parcel source) {
            return new Joke(source);
        }

        @Override
        public Joke[] newArray(int size) {
            return new Joke[size];
        }
    };

    private Joke(Parcel source){
        this.type = source.readString();
        this.value = new Value();
        this.value.id = source.readInt();
        this.value.joke = source.readString();
        this.value.categories = source.createStringArray();
    }

    public Joke(ContentValues values){
        value = new Value();

        String categories = values.getAsString(JokeProviderContract.JokesTable.categories);
        this.type = values.getAsString(JokeProviderContract.JokesTable.type);
        this.value.joke = values.getAsString(JokeProviderContract.JokesTable.joke);
        this.value.id = values.getAsInteger(JokeProviderContract.JokesTable.joke_id);
        if (categories != null){
            this.value.categories = categories.split(" ");
        }
    }
}
