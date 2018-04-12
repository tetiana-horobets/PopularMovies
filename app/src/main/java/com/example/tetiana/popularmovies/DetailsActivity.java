package com.example.tetiana.popularmovies;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tetiana.popularmovies.DatabaseFavoriteMovie.FavoriteMovieContract;
import com.example.tetiana.popularmovies.Service.RestAdapter;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class DetailsActivity extends AppCompatActivity {

    private TextView tvOriginalTitle;
    private ImageView imPosterPath;
    private ImageView imDetailsActivity;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private TextView tvVoteAverage;
    private String posterPath;
    private String backdropPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String movie_id = getIntent().getStringExtra("name");
        setContentView(R.layout.activity_details);

        imDetailsActivity = findViewById(R.id.backdrop_path);
        imPosterPath = findViewById(R.id.poster_path);
        tvOriginalTitle = findViewById(R.id.original_title);
        tvReleaseDate =  findViewById(R.id.release_date);
        tvOverview =  findViewById(R.id.overview);
        tvVoteAverage =  findViewById(R.id.vote_average);
        Button ibFavoriteMovie =  findViewById(R.id.favorite_movie);

        RestAdapter.getService().getDetail(Integer.parseInt(movie_id), new Callback<Details>() {

            @Override
            public void success(Details movieDatail, Response response) {

                tvOriginalTitle.setText(movieDatail.getMovieOriginalTitle());
                tvReleaseDate.setText(movieDatail.getMovieReleaseDate());
                tvOverview.setText(movieDatail.getMovieOverview());
                tvVoteAverage.setText(movieDatail.getMovieVoteAverage());
                posterPath = movieDatail.getMoviePosterPath();
                backdropPath = movieDatail.getMovieBackdropPath();
                Picasso.with(getApplicationContext())
                        .load(posterPath)
                        .into(imPosterPath);

                Picasso.with(getApplicationContext())
                        .load(backdropPath)
                        .into(imDetailsActivity);
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });

        ibFavoriteMovie.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                String addMovieTitleToDB = tvOriginalTitle.getText().toString();
                String addMovieReleaseDataToDB = tvReleaseDate.getText().toString();
                String addMovieOverlieToDB = tvOverview.getText().toString();
                String addMovieVoteAverageToDB = tvVoteAverage.getText().toString();

                values.put(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_ID, movie_id);
                values.put(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_POSTER_PATH, posterPath);
                values.put(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_RELEASE_DATA, addMovieReleaseDataToDB );
                values.put(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_OVERVIEW, addMovieOverlieToDB);
                values.put(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_VOTE_AVERAGE, addMovieVoteAverageToDB );
                values.put(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_TITLE, addMovieTitleToDB);
                values.put(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_BACKDROP_PATH, backdropPath);

                getContentResolver().insert(FavoriteMovieContract.TitleAndIDsOfMovies.CONTENT_URI, values);
            }
        });
    }
}
