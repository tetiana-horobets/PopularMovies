package com.example.tetiana.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Parcelable {

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("id")
    private int movie_id;

    public Movie() {}

    protected Movie(Parcel in) {
        poster = in.readString();
        movie_id = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    String getPoster() {
        return "http://image.tmdb.org/t/p/w500" + poster;
    }

    int getMovie_id(){
        return movie_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster);
        dest.writeInt(movie_id);
    }

    public static class MovieResult implements Parcelable {
        private ArrayList<Movie> results;

        protected MovieResult(Parcel in) {
            results = in.createTypedArrayList(Movie.CREATOR);
        }

        public static final Creator<MovieResult> CREATOR = new Creator<MovieResult>() {
            @Override
            public MovieResult createFromParcel(Parcel in) {
                return new MovieResult(in);
            }

            @Override
            public MovieResult[] newArray(int size) {
                return new MovieResult[size];
            }
        };

        ArrayList<Movie> getResults() {
            return results;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(results);
        }
    }
}
