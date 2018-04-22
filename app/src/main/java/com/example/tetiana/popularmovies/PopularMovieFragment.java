package com.example.tetiana.popularmovies;

import android.content.Intent;
import android.os.Bundle;
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

public class PopularMovieFragment extends Fragment implements MovieAdapter.ListItemClickListener {

    private  ArrayList<Movie> popularMovies = new ArrayList<>();
    private  MovieAdapter movieAdapter;
    RecyclerView mRecyclerView;

    public static PopularMovieFragment newInstance() {
        PopularMovieFragment fragment = new PopularMovieFragment();
        final Bundle arguments = new Bundle();
        arguments.putParcelableArrayList("popularMovies", fragment.showPopularMovie());
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("popularMovies")) {
            popularMovies = getArguments().getParcelableArrayList("popularMovies");
        } else {
            throw new IllegalArgumentException("Must be created through newInstance(...)");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_popular_movie, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.rv_show_movie);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            popularMovies = savedInstanceState.getParcelableArrayList("saveStateMovies");
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), new NumberOfColumns().numberOfColumns(getActivity())));
        movieAdapter = new MovieAdapter(getActivity().getApplicationContext(), popularMovies, this);
        mRecyclerView.setAdapter(movieAdapter);
    }

    public ArrayList<Movie> showPopularMovie() {
        RestAdapter.getService().getPopularMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                popularMovies = movieResult.getResults();
                movieAdapter.setMovieList(popularMovies);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
        return popularMovies;
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
        outState.putParcelableArrayList("saveStateMovies", showPopularMovie());
    }
}
