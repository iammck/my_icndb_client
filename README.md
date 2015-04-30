#Testing Running and Debugging from Android Studio#

With a connected android test phone or a running simulator, The command
.\gradlew test connectedAndroidTest
can be run from the terminal in Android Studio to build and run the test and androidTest tasks.  Testing tasks produce reports in the app/build/reports/test and app/build/output/reports/androidTest directories.

In Android studio the application can be ran or debugged using any build variant apk.

You can also download some alpha releases by first joining the group at
https://groups.google.com/d/forum/commckalphatesting

#About#
Following a YouTube example demonstrating the android gradle wrapper with an application that uses Rest and Json to get and interpret Chuck Norris Jokes from the internet. The video is available at https://www.youtube.com/watch?v=0bhbQdZLpIE .The author,Kenneth Kousen, uses Spring for Android to retrieve jokes from http://www.icndb.com/ and displays them to the user. The author also uses Robotium for testing the user interface.

#Objective#
Produced Jokes about a main character (ie chuck norris) when the user interacts with the app and display the jokes one at a time.
The jokes are acquired over a network by using a RestTeplate with a gson converter component to convert html objects into json objects. The Project has six build types and two flavors, producing 12 APKs.
The different build types only vary by the main character name in res dir. The flavors vary the ui experience between a Button and a ViewPager.
The Button flavor will show one joke per a click. This version is similar to the one found in the video.
The ViewPager flavor uses a ContentProvider to keep track of all the jokes. On start up the JokeViewerFragment loads the provider's data into the adapter plus one empty JokeFragment on the far right.. The user can slide back and forth between jokes. When the user slides far enought to the right to load the empty JokeFragment, that fragment will fetch a joke from online, store it in the db and update itself. The JokeViewerPager, notices the db update and adds a new blank fragment to the adapter.

#Personal Goals#
Primary: Learn more about Android Studio and the Gradle Wrapper for building multiple flavors.
Secondary: Learn more about REST, JSON, Gson, Mockito, Robotium, ContentProviders, ViewPagers, adapters, CardViews, project design and management, google play alpha and production release.

#How jokes are acquired#
The jokes are requested from the site  http://api.icndb.com using HTTP GET.
To fetch a random joke one can use http://api.icndb.com/jokes/random
The result of such a fetch is a json object of the form:
{ "type": "success", "value": { "id": 553, "joke": "China lets Chuck Norris search for porn on Google.", "categories": ["explicit"] } }
Internally, a RestTemplate object is used to retrieve the json item. The RestTemplate then process the item into a Joke object.
