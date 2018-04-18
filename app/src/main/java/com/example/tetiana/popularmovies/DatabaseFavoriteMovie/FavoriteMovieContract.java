package com.example.tetiana.popularmovies.DatabaseFavoriteMovie;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavoriteMovieContract {

    static final String AUTHORITY = "com.example.tetiana.popularmovies";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    static final String PATH_TASKS = "favoriteMovieList";

    public static final class TitleAndIDsOfMovies implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

        static final String TABLE_NAME = "favoriteMovieList";
        public static final String COLUMN_FAVORITE_MOVIE_TITLE = "favoriteMovieTitle";
        public static final String COLUMN_FAVORITE_MOVIE_ID = "favoriteMovieID";
        public static final String COLUMN_FAVORITE_MOVIE_POSTER_PATH = "favoriteMoviePosterPath";
        public static final String COLUMN_FAVORITE_MOVIE_BACKDROP_PATH = "favoriteMovieBackdropPath";
        public static final String COLUMN_FAVORITE_MOVIE_RELEASE_DATA = "favoriteMovieReleaseDate";
        public static final String COLUMN_FAVORITE_MOVIE_OVERVIEW = "favoriteMovieOverview";
        public static final String COLUMN_FAVORITE_MOVIE_VOTE_AVERAGE = "favoriteMovieVoteAverage";
    }
}
