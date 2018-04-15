package com.example.tetiana.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private List<String> videoList;
    private Context context;
    private final ListItemClickListener mOnClickListener;
    private int clickedPosition;

    public VideoAdapter(List<String> videoList, Context context, ListItemClickListener listener) {
        this.videoList = videoList;
        this.context = context;
        mOnClickListener = listener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @Override
    public void onBindViewHolder(VideoAdapter.VideoViewHolder holder, int position) {
        String url = "https://img.youtube.com/vi/"+ videoList.get(position) +"/0.jpg";

        Picasso.with(context)
                .load(url)
                .placeholder(R.color.colorAccent)
                .into(holder.ivVideo);

    }

    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.video_list_item, parent, false);
        return new VideoAdapter.VideoViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return (videoList == null) ? 0 : videoList.size();
    }

    void setVideoList(List<String> videoList) {
        this.videoList = new ArrayList<>();
        this.videoList.addAll(videoList);
        notifyDataSetChanged();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivVideo;
        public VideoViewHolder(View itemView) {
            super(itemView);
            ivVideo = itemView.findViewById(R.id.iv_video);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
    public String getVideoKey() {
        return String.valueOf(videoList.get(clickedPosition));
    }
}
