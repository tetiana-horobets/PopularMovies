package com.example.tetiana.popularmovies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("id")
    private int movie_id;

    public Movie() {}

    String getPoster() {
        return "http://image.tmdb.org/t/p/w500" + poster;
    }

    int getMovie_id(){
        return movie_id;
    }

    public static class MovieResult {
        private List<Movie> results;

        List<Movie> getResults() {
            return results;
        }
    }
}
