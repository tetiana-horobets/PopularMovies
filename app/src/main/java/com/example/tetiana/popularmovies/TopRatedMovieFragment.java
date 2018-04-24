package com.example.tetiana.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tetiana.popularmovies.Service.RestAdapter;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TopRatedMovieFragment extends Fragment implements MovieAdapter.ListItemClickListener {

    private ArrayList<Movie> topRatedMovies = new ArrayList<>();
    private  MovieAdapter movieAdapter;
    RecyclerView mRecyclerView;
    Parcelable savedRecyclerLayoutState;

    public static TopRatedMovieFragment newInstance() {
        TopRatedMovieFragment fragment = new TopRatedMovieFragment();
        final Bundle arguments = new Bundle();
        arguments.putParcelableArrayList("topRatedMovies", fragment.showTopMovie());
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("topRatedMovies")) {
            topRatedMovies = getArguments().getParcelableArrayList("topRatedMovies");
        } else {
            throw new IllegalArgumentException("Must be created through newInstance(...)");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_rated_movie, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.rv_show_top_movie);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));
        movieAdapter = new MovieAdapter(getActivity().getApplicationContext(), topRatedMovies, this);
        mRecyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            topRatedMovies = savedInstanceState.getParcelableArrayList("saveStateTopRatedMovies");
            savedRecyclerLayoutState = savedInstanceState.getParcelable("saveScrollPositionMovie");
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    public ArrayList<Movie> showTopMovie() {
        RestAdapter.getService().getTopRatedMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                topRatedMovies = movieResult.getResults();
                movieAdapter.setMovieList(topRatedMovies);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
        return topRatedMovies;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        String id = movieAdapter.getId();
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("detailsMovie", id);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("saveStateTopRatedMovies", showTopMovie());
        outState.putParcelable("saveScrollPositionMovie", mRecyclerView.getLayoutManager().onSaveInstanceState());
    }
}
