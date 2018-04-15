package com.example.tetiana.popularmovies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.example.tetiana.popularmovies.DatabaseFavoriteMovie.FavoriteMovieContract;

public class Favorite extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>, FavoriteMovieAdapter.ListItemClickListener {

    private static final String TAG = Favorite.class.getSimpleName();
    private static final int TASK_LOADER_ID = 0;
    private FavoriteMovieAdapter mAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


        mRecyclerView = (RecyclerView) this.findViewById(R.id.rv_show_favorite_movie);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mAdapter = new FavoriteMovieAdapter(this,  this);
        mRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                int id = (int) viewHolder.itemView.getTag();
                String stringId = Integer.toString(id);
                Uri uri = FavoriteMovieContract.TitleAndIDsOfMovies.CONTENT_URI;
                uri = uri.buildUpon().appendPath(stringId).build();
                getContentResolver().delete(uri, null, null);
                getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, Favorite.this);
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
    }

        @SuppressLint("StaticFieldLeak")
        @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new AsyncTaskLoader<Cursor>(this) {
                Cursor cursor = null;
                @Override
                protected void onStartLoading() {
                    if (cursor != null) {
                        deliverResult(cursor);
                    } else {
                        forceLoad();
                    }
                }

                @Override
                public Cursor loadInBackground() {
                    try {
                        return getContentResolver().query(FavoriteMovieContract.TitleAndIDsOfMovies.CONTENT_URI,
                                null,
                                null,
                                null,
                                null);

                    } catch (Exception e) {
                        Log.e(TAG, "Failed to asynchronously load data.");
                        e.printStackTrace();
                        return null;
                    }
                }

                public void deliverResult(Cursor data) {
                    cursor = data;
                    super.deliverResult(data);
                }
            };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        String id = mAdapter.getMovie_id();
        Context context = Favorite.this;
        Class destinationActivity = FavoriteDetails.class;
        Intent favoriteIntent = new Intent(context, destinationActivity);
        favoriteIntent.putExtra("id", id);
        startActivity(favoriteIntent);
    }
}

