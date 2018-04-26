## PopularMovies
 Popular Movies is the project which I made from scratch for [Android Developer Nanodegree Program](https://eu.udacity.com/course/android-developer-nanodegree-by-google--nd801). The purpose of this project was to build the app, to help users discover popular and highly rated movies on the web. It displays a scrolling grid of movie trailers, launches a details screen whenever a particular movie is selected, allows users to save favorites, play trailers, and read user reviews. This app utilizes core Android user interface components and fetches movie information using themoviedb.org web API.
### API used
 The Movie Data Base (TMDb)
 
 NOTE: Add your API key to Buil.gradle file as specified below in order to run the app.
 ```sh
  buildTypes.each{
         it.buildConfigField 'String', 'MY_MOVIE_DB_API_KEY',"\"write_your_key_here\""
     }
 }
 ```
 You can obtain a key from themoviedb.org
### Learning Outcomes:

* Implementation of Content Providers with SQLite Database.
* Updating UI with CursorAdapters and Loaders.
* Using SyncAdapter for Updating data Synchronously.
* Fragments.

### Libraries

* [Retrofit](https://github.com/square/retrofit) - A type-safe HTTP client for Android and Java
* [Picasso](http://square.github.io/picasso/) - A powerful library that handles image loading and caching in the app
* [Support Library Packages](https://developer.android.com/topic/libraries/support-library/packages)

> Click on image and open video on youtube

[![IMAGE ALT TEXT HERE](http://img.youtube.com/vi/kSzq3fyg85w/0.jpg)](https://www.youtube.com/watch?v=kSzq3fyg85w)