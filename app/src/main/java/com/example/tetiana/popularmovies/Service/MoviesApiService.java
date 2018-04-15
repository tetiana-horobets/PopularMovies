package com.example.tetiana.popularmovies.Service;

import android.telecom.Call;

import com.example.tetiana.popularmovies.Details;
import com.example.tetiana.popularmovies.Movie;
import com.example.tetiana.popularmovies.Video;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
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

    @GET("/movie/{movie_id}/videos")
    void getMovieTrailer(
            @Path("movie_id") int id,
            Callback<MovieTrailerResponse> callback
    );

    class MovieTrailerResponse {
        private List<Video> results;

        public List<Video> getResults() {
            return results;
        }
    }

}
