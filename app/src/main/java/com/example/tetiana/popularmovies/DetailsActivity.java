package com.example.tetiana.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tetiana.popularmovies.DatabaseFavoriteMovie.FavoriteMovieContract;
import com.example.tetiana.popularmovies.Service.MoviesApiService;
import com.example.tetiana.popularmovies.Service.RestAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class DetailsActivity extends AppCompatActivity implements VideoAdapter.ListItemClickListener {

    private TextView tvOriginalTitle;
    private ImageView imPosterPath;
    private ImageView imDetailsActivity;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private TextView tvVoteAverage;
    private String posterPath;
    private String backdropPath;
    private VideoAdapter videoAdapter;
    private List<String> videoList = new ArrayList<>();
    final List<Review> reviewList = new ArrayList<>();
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String movie_id = getIntent().getStringExtra("name");
        setContentView(R.layout.activity_details);

        imDetailsActivity = findViewById(R.id.backdrop_path);
        imPosterPath = findViewById(R.id.poster_path);
        tvOriginalTitle = findViewById(R.id.original_title);
        tvReleaseDate = findViewById(R.id.release_date);
        tvOverview = findViewById(R.id.overview);
        tvVoteAverage = findViewById(R.id.vote_average);
        final Button ibFavoriteMovie =   findViewById(R.id.favorite_movie);


        RecyclerView videoRV = findViewById(R.id.rv_show_video);
        videoRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        videoAdapter = new VideoAdapter(videoList, this, this);
        videoRV.setAdapter(videoAdapter);

        RecyclerView reviewRV = findViewById(R.id.rv_show_review);
        reviewRV.setLayoutManager(new GridLayoutManager(this, 1));
        reviewAdapter = new ReviewAdapter(reviewList, this);
        reviewRV.setAdapter(reviewAdapter);

        RestAdapter.getService().getDetail(Integer.parseInt(movie_id), new Callback<Details>() {

            @Override
            public void success(Details details, Response response) {

                tvOriginalTitle.setText(details.getMovieOriginalTitle());
                tvReleaseDate.setText(details.getMovieReleaseDate());
                tvOverview.setText(details.getMovieOverview());
                tvVoteAverage.setText(details.getMovieVoteAverage());
                posterPath = details.getMoviePosterPath();
                backdropPath = details.getMovieBackdropPath();
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

        RestAdapter.getService().getMovieTrailer(Integer.parseInt(movie_id), new Callback<MoviesApiService.MovieTrailerResponse>() {

            @Override
            public void success(MoviesApiService.MovieTrailerResponse movieTrailerResponse, Response response) {
                for (int i = 0; i < movieTrailerResponse.getResults().size(); i++){
                    videoList.add(movieTrailerResponse.getResults().get(i).getKey());
                }
                videoAdapter.notifyDataSetChanged();
            }
            @Override
            public void failure(RetrofitError error) {

            }
        });

        RestAdapter.getService().getMovieRevies(Integer.parseInt(movie_id), new Callback<MoviesApiService.MovieReviewResponse>() {
            @Override
            public void success(MoviesApiService.MovieReviewResponse movieReviewResponse, Response response) {
                reviewList.addAll(movieReviewResponse.getResults());
                reviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        Cursor cursor = getContentResolver()
                .query(FavoriteMovieContract.TitleAndIDsOfMovies.CONTENT_URI,
                        null,
                        FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_ID + " = " + DatabaseUtils.sqlEscapeString(movie_id),
                        null, null);

        if (cursor.getCount() == 0) {
            // not found in database
            ibFavoriteMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ibFavoriteMovie.setEnabled(false);
                    ContentValues values = new ContentValues();
                    String addMovieTitleToDB = tvOriginalTitle.getText().toString();
                    String addMovieReleaseDataToDB = tvReleaseDate.getText().toString();
                    String addMovieOverlieToDB = tvOverview.getText().toString();
                    String addMovieVoteAverageToDB = tvVoteAverage.getText().toString();

                    values.put(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_ID, movie_id);
                    values.put(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_POSTER_PATH, posterPath);
                    values.put(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_RELEASE_DATA, addMovieReleaseDataToDB);
                    values.put(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_OVERVIEW, addMovieOverlieToDB);
                    values.put(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_VOTE_AVERAGE, addMovieVoteAverageToDB);
                    values.put(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_TITLE, addMovieTitleToDB);
                    values.put(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_BACKDROP_PATH, backdropPath);

                    getContentResolver().insert(FavoriteMovieContract.TitleAndIDsOfMovies.CONTENT_URI, values);
                }
            });
        }
    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
        String id = videoAdapter.getVideoKey();
        watchYoutubeVideo(getApplicationContext(), id);
    }

    public static void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

}
