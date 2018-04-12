package com.example.tetiana.popularmovies.Service;

import com.example.tetiana.popularmovies.Details;
import com.example.tetiana.popularmovies.Movie;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface MoviesApiService {
    @GET("/movie/popular")
    void getPopularMovies(Callback<Movie.MovieResult> cb);

    @GET("/movie/top_rated")
    void getTopRatedMovies(Callback<Movie.MovieResult> cb);

    @GET("/movie/{movie_id}")
    void getDetail(
            @Path("movie_id") int id,
            Callback<Details> callback
    );
}
