## BakingApp
 BakingApp is the project which I made from scratch for [Android Developer Nanodegree Program](https://eu.udacity.com/course/android-developer-nanodegree-by-google--nd801). The app takes some recipes in JSON format from the web and shows ingredients, steps, videos. Thei aim of the app is to learn how to:
 - Use MediaPlayer/Exoplayer to display videos
 - Handle error cases in Android
 - Add a widget to your app experience
 - Leverage a third-party library in your app
 - Use Fragments to create a responsive design that works on phones and tablets

> Click on image and open video on youtube

[![IMAGE ALT TEXT HERE](http://img.youtube.com/vi/kSzq3fyg85w/0.jpg)](https://www.youtube.com/watch?v=kSzq3fyg85w)

### About this app
 - Retrives a list of recipes from given url (By Udacity)
 - On Clicking on a recipe it shows the ingredients and steps to cook
 - On tablet if a step is clicked it shows in the right panel of the screen
 - On phone if a step is clicked it launches a new activity which displays video instruction
 - This app also has a widget. If added on homescreen shows the selected recipes' ingredients
 - The widget will update ingredients when user selects a recipe in the app
 - The source code has espresso unit tests

###  This app features or uses the following components of android

* This app handles Activity and Fragment lifeCycles
* This app maintains instanceState on configuration change (Activity and Fragment).
* Passing data between activities and fragments.
* Fragments and re-use of the same fragment in different activities.
* Desiging layouts for different screen sizes(Phone and tablet) and orientation (Landscape)
* Loading data from network, Loading and caching Image form network.
* JSON data handling and serialization.
* Widgets
* Streaming video using ExoPlayer.
* RecyclerViews, CardViews
* Espresso Unit Tests.

### This app makes use of the following external libraries and resources

* Recipe data is provided by Udacity
* [Picasso](http://square.github.io/picasso/) - a powerful library that handles image loading and caching in the app
* [ButterKnife](http://jakewharton.github.io/butterknife/) 
* [ExoPlayer](https://github.com/google/ExoPlayer)
* [Espresso](https://developer.android.com/training/testing/espresso/)
* [Gson](https://github.com/google/gson)
* [Support Library Packages](https://developer.android.com/topic/libraries/support-library/packages)
