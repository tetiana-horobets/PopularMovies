package com.example.tetiana.popularmovies;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class FavoriteDetails extends AppCompatActivity {
    private String posterPath;
    private String backdropPath;

    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ImageView imDetailsActivity = findViewById(R.id.backdrop_path);
        ImageView imPosterPath = findViewById(R.id.poster_path);
        TextView tvOriginalTitle = findViewById(R.id.original_title);
        TextView tvReleaseDate = findViewById(R.id.release_date);
        TextView tvOverview = findViewById(R.id.overview);
        TextView tvVoteAverage = findViewById(R.id.vote_average);
    }
}
