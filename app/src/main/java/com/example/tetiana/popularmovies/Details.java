package com.example.tetiana.popularmovies;

import com.google.gson.annotations.SerializedName;

public class Details {

    @SerializedName("id")
    private String movieID;

    @SerializedName("original_title")
    private String movieOriginalTitle;

    @SerializedName("poster_path")
    private String moviePosterPath;

    @SerializedName("backdrop_path")
    private String movieBackdropPath;

    @SerializedName("release_date")
    private String movieReleaseDate;

    @SerializedName("overview")
    private String movieOverview;

    @SerializedName("vote_average")
    private String movieVoteAverage;

    String getMovieOriginalTitle() {
        return movieOriginalTitle;
    }

    String getMovieBackdropPath() {
        return "http://image.tmdb.org/t/p/w500" + movieBackdropPath;
    }

    String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    String getMovieOverview() {
        return movieOverview;
    }

    String getMovieVoteAverage() {
        return movieVoteAverage;
    }

    String getMoviePosterPath() {
        return "http://image.tmdb.org/t/p/w500" + moviePosterPath;
    }

    String getMovieID() {
        return movieID;
    }
}
