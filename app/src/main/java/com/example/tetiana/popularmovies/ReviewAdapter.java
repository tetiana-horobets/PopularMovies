package com.example.tetiana.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private List<Review> reviewList;
    private Context context;

    public ReviewAdapter(List<Review> reviewList, Context context) {
        this.reviewList = reviewList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        String author = reviewList.get(position).getAuthor();
        String review = reviewList.get(position).getContent();

        holder.reviewAuthor.setText(author);
        holder.reviewContext.setText(review);

    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.review_list_item, parent, false);
        return new ReviewAdapter.ReviewHolder(view);
    }

    @Override
    public int getItemCount() {
        return (reviewList == null) ? 0 : reviewList.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {
        TextView reviewAuthor;
        TextView reviewContext;
        public ReviewHolder(View itemView) {
            super(itemView);
            reviewAuthor = itemView.findViewById(R.id.review_author);
            reviewContext = itemView.findViewById(R.id.review_content);
        }
    }
}
