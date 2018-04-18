package com.example.tetiana.popularmovies;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tetiana.popularmovies.DatabaseFavoriteMovie.FavoriteMovieContract;
import com.squareup.picasso.Picasso;


public class FavoriteDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        final String movie_id = getIntent().getStringExtra("id");
        ImageView imBackdropPath = findViewById(R.id.backdrop_path);
        ImageView imPosterPath = findViewById(R.id.poster_path);
        TextView tvOriginalTitle = findViewById(R.id.original_title);
        TextView tvReleaseDate = findViewById(R.id.release_date);
        TextView tvOverview = findViewById(R.id.overview);
        TextView tvVoteAverage = findViewById(R.id.vote_average);

        Cursor cursor = getContentResolver()
                .query(FavoriteMovieContract.TitleAndIDsOfMovies.CONTENT_URI,
                        null,
                        FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_ID + " = " + DatabaseUtils.sqlEscapeString(movie_id),
                        null, null);

        cursor.moveToNext();

        String title = cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_TITLE));
        String releaseDate = cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_RELEASE_DATA));
        String overview = cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_OVERVIEW));
        String voteAverage = cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_VOTE_AVERAGE));
        String posterPath = cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_POSTER_PATH));
        String backdropPath = cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_BACKDROP_PATH));

        tvOriginalTitle.setText(title);
        tvReleaseDate.setText(releaseDate);
        tvOverview.setText(overview);
        tvVoteAverage.setText(voteAverage);

        Picasso.with(getApplicationContext())
                .load(posterPath)
                .into(imPosterPath);

        Picasso.with(getApplicationContext())
                .load(backdropPath)
                .into(imBackdropPath);
    }
}
