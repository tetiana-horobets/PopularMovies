package com.example.tetiana.popularmovies;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.tetiana.popularmovies.DatabaseFavoriteMovie.FavoriteMovieContract;


public class Favorite extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>, FavoriteMovieAdapter.ListItemClickListener {

    private static final String TAG = Favorite.class.getSimpleName();
    private static final int TASK_LOADER_ID = 0;
    private FavoriteMovieAdapter mAdapter;
    RecyclerView mRecyclerView;
    Cursor cursor = null;
    private int menu_selection = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        mRecyclerView = (RecyclerView) this.findViewById(R.id.rv_show_favorite_movie);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mAdapter = new FavoriteMovieAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition(); //get position which is swipe

                if ((direction == ItemTouchHelper.LEFT )||(direction == ItemTouchHelper.RIGHT)) {    //if swipe left

                    AlertDialog.Builder builder = new AlertDialog.Builder(Favorite.this); //alert for confirm to delete
                    builder.setMessage("Are you sure to delete?");    //set message

                    builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() { //when click on DELETE
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAdapter.notifyItemRemoved(position);
                            int id = (int) viewHolder.itemView.getTag();
                            String stringId = Integer.toString(id);
                            Uri uri = FavoriteMovieContract.TitleAndIDsOfMovies.CONTENT_URI;
                            uri = uri.buildUpon().appendPath(stringId).build();
                            getContentResolver().delete(uri, null, null);
                            getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, Favorite.this);
                            mAdapter.movieListId.remove(position);
                            restart();
                        }
                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAdapter.notifyItemRemoved(position + 1);    //notifies the RecyclerView Adapter that data in adapter has been removed at a particular position.
                            mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());   //notifies the RecyclerView Adapter that positions of element in adapter has been changed from position(removed element index to end of list), please update it.
                            return;
                        }
                    }).show();  //show alert dialog
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                int id = (int) viewHolder.itemView.getTag();
//                String stringId = Integer.toString(id);
//                Uri uri = FavoriteMovieContract.TitleAndIDsOfMovies.CONTENT_URI;
//                uri = uri.buildUpon().appendPath(stringId).build();
//                getContentResolver().delete(uri, null, null);
//                getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, Favorite.this);
//            }
//
//        }).attachToRecyclerView(mRecyclerView);


    }

    private void restart() {
        Intent intent = new Intent(this, this.getClass());
        finish();
        ;
        this.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.isChecked()) {
            item.setChecked(true);
        } else {
            item.setChecked(true);
            menu_selection = item.getItemId();
        }
        switch (item.getItemId()) {

            case R.id.menu_popular:
                Context context = Favorite.this;
                Class destinationActivity = MainActivity.class;
                Intent intent = new Intent(context, destinationActivity);
                startActivity(intent);
                return true;

            case R.id.menu_top:
                Context context2 = Favorite.this;
                Class destinationActivity2 = MainActivity.class;
                Intent intent2 = new Intent(context2, destinationActivity2);
                startActivity(intent2);
                return true;

            case R.id.menu_favorite:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

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
        String movie_id = mAdapter.getMovie_id();
        Context context = Favorite.this;
        Class destinationActivity = FavoriteDetails.class;
        Intent favoriteIntent = new Intent(context, destinationActivity);
        favoriteIntent.putExtra("id", movie_id);
        startActivity(favoriteIntent);
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
}

