package com.example.tetiana.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.tetiana.popularmovies.Service.InternetConnection;
import com.example.tetiana.popularmovies.Service.RestAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener {

    List<Movie> movies = new ArrayList<>();
    private MovieAdapter mAdapter;
    private int menu_selection = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = findViewById(R.id.rv_show_movie);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        TextView mEmptyStateTextView = findViewById(R.id.empty_view);
        mAdapter = new MovieAdapter(this, movies, this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setMovieList(movies);

        if (!InternetConnection.isOnline()){
            mEmptyStateTextView.setText(R.string.no_internet);
        }

        showPopularMovie();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        String id = mAdapter.getId();
        Context context = MainActivity.this;
        Class destinationActivity = DetailsActivity.class;
        Intent intent = new Intent(context, destinationActivity);
        intent.putExtra("name", id);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        if (menu_selection != -1) {

            MenuItem selected = menu.findItem(menu_selection);
            selected.setChecked(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
            menu_selection = item.getItemId();
        }

        switch (item.getItemId()) {

            case R.id.menu_popular:
                showPopularMovie();
                return true;

            case R.id.menu_top:
                showTopMovie();
                return true;

            case R.id.menu_favorite:
                Context context = MainActivity.this;
                Class destinationActivity = Favorite.class;
                Intent intent = new Intent(context, destinationActivity);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("selection", menu_selection);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        menu_selection = savedInstanceState.getInt("selection");

    }

    private void showPopularMovie() {
        RestAdapter.getService().getPopularMovies(new ChangeMe(mAdapter));
    }

    private void showTopMovie() {
        RestAdapter.getService().getTopRatedMovies(new ChangeMe(mAdapter));
    }

    //TODO rename

    private static class ChangeMe implements Callback<Movie.MovieResult> {

        private MovieAdapter mAdapter;

        ChangeMe(MovieAdapter mAdapter) {
            this.mAdapter = mAdapter;
        }

        @Override
        public void success(Movie.MovieResult movieResult, Response response) {
            mAdapter.setMovieList(movieResult.getResults());
        }

        @Override
        public void failure(RetrofitError error) {
            error.printStackTrace();
        }
    }
}
