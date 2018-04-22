package com.example.tetiana.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>
{
    private ArrayList<Movie> movieList;
    final private ListItemClickListener mOnClickListener;
    private Context context;

    private int clickedPosition;

    MovieAdapter(Context context, ArrayList<Movie> movieList, ListItemClickListener listener)
    {
        this.context = context;
        this.movieList = movieList;
        mOnClickListener = listener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position)
    {
        Movie movie = movieList.get(position);
        Picasso.with(context)
                .load(movie.getPoster())
                .placeholder(R.color.colorAccent)
                .into(holder.ivPoster);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_list_item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return (movieList == null) ? 0 : movieList.size();
    }

    void setMovieList(ArrayList<Movie> movieList) {
        this.movieList = new ArrayList<>();
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivPoster;
        MovieViewHolder(View itemView)
        {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_popular_movie);
            itemView.setOnClickListener(this);
    }

        @Override
        public void onClick(View view) {
            clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
    public String getId() {
        return String.valueOf(movieList.get(clickedPosition).getMovie_id());
    }
}
