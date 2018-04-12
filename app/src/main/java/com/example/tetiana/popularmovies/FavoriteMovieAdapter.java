package com.example.tetiana.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tetiana.popularmovies.DatabaseFavoriteMovie.FavoriteMovieContract;
import com.squareup.picasso.Picasso;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.GuestViewHolder> {

    private Cursor mCursor;
    private Context mContext;
    final private FavoriteMovieAdapter.ListItemClickListener mOnClickListener;
    private int columIndexs;

    FavoriteMovieAdapter(Context context, FavoriteMovieAdapter.ListItemClickListener listener) {
        this.mContext = context;
        this.mOnClickListener = listener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.favorite_list_item, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {
        columIndexs = mCursor.getColumnIndex(FavoriteMovieContract.TitleAndIDsOfMovies._ID);
        int getMoviePosterPatch = mCursor.getColumnIndex(FavoriteMovieContract.TitleAndIDsOfMovies.COLUMN_FAVORITE_MOVIE_POSTER_PATH);

        mCursor.moveToPosition(position);
        final int id = mCursor.getInt(columIndexs);

        String posterPatch = mCursor.getString(getMoviePosterPatch);
        holder.itemView.setTag(id);
        Picasso.with(mContext.getApplicationContext())
                .load(posterPatch)
                .into( holder.posterPatch);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();    }

    class GuestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView posterPatch;

        GuestViewHolder(View itemView) {
            super(itemView);
            posterPatch = (ImageView) itemView.findViewById(R.id.posterPatch);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

    public String getMovie_id() {
        return String.valueOf(mCursor.getInt(columIndexs));
    }

    void swapCursor(Cursor c) {
        if (mCursor == c) {
            return;
        }
        this.mCursor = c;
        if (c != null) {
            this.notifyDataSetChanged();
        }
    }
}
