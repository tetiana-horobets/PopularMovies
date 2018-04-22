package com.example.tetiana.popularmovies;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tetiana.popularmovies.DatabaseFavoriteMovie.FavoriteMovieContract;


public class FavoriteMovieFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>, FavoriteMovieAdapter.ListItemClickListener {

    private static final String TAG = FavoriteMovieFragment.class.getSimpleName();
    private static final int TASK_LOADER_ID = 0;
    private FavoriteMovieAdapter favoriteMovieAdapter;
    RecyclerView mRecyclerView;
    Cursor cursor = null;

    public static FavoriteMovieFragment newInstance() {
        return new FavoriteMovieFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.rv_show_favorite_movie);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), new NumberOfColumns().numberOfColumns(getActivity())));

        favoriteMovieAdapter = new FavoriteMovieAdapter(getActivity().getApplicationContext(), this);
        mRecyclerView.setAdapter(favoriteMovieAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                if ((direction == ItemTouchHelper.LEFT )||(direction == ItemTouchHelper.RIGHT)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure to delete?");

                    builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() { //when click on DELETE
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            int id = (int) viewHolder.itemView.getTag();
                            String stringId = Integer.toString(id);
                            Uri uri = FavoriteMovieContract.TitleAndIDsOfMovies.CONTENT_URI;
                            uri = uri.buildUpon().appendPath(stringId).build();
                            getActivity().getContentResolver().delete(uri, null, null);
                            getActivity().getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, FavoriteMovieFragment.this);
                            favoriteMovieAdapter.movieListId.remove(position);
                            getActivity().recreate();


                        }
                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            favoriteMovieAdapter.notifyItemRemoved(position + 1);
                            favoriteMovieAdapter.notifyItemRangeChanged(position, favoriteMovieAdapter.getItemCount());
                        }
                    }).show();
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
         // notify adapter
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(getActivity()) {

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
                    return getActivity().getContentResolver().query(FavoriteMovieContract.TitleAndIDsOfMovies.CONTENT_URI,
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
        favoriteMovieAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favoriteMovieAdapter.swapCursor(null);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        String movie_id = favoriteMovieAdapter.movieListId.get(clickedItemIndex);
        Intent favoriteIntent = new Intent(getActivity(), FavoriteDetails.class);
        favoriteIntent.putExtra("favoriteDetailsMovie", movie_id);
        startActivity(favoriteIntent);
    }
}
