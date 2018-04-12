package com.example.tetiana.popularmovies.DatabaseFavoriteMovie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteMovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favoriteMovieList.db";
    private static final int DATABASE_VERSION = 1;

    FavoriteMovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITE_MOVIE_TABLE = "CREATE TABLE " + FavoriteMovieContract.TitleAndIDsOfMovies.TABLE_NAME + " (" +
                FavoriteMovieContract.TitleAndIDsOfMovies._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_TITLE + " TEXT NOT NULL, " +
                FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_ID + " TEXT NOT NULL, " +
                FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_POSTER_PATH + " TEXT NOT NULL, " +
                FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_BACKDROP_PATH + " TEXT NOT NULL, " +
                FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_RELEASE_DATA + " TEXT NOT NULL, " +
                FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_VOTE_AVERAGE + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteMovieContract.TitleAndIDsOfMovies.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
