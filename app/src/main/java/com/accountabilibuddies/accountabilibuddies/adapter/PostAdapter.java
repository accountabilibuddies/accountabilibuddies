package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.modal.Post;
import com.accountabilibuddies.accountabilibuddies.util.Constants;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Post> postList;
    private Context context;
    private final int POST_WITH_IMAGE = 0, POST_WITH_VIDEO = 1,
                    POST_WITH_TEXT = 2, POST_WITH_LOCATION = 3;

    public PostAdapter(Context context, List<Post> postList) {
        this.postList = postList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        switch (postList.get(position).getType()) {
            case Constants.TYPE_VIDEO:
                return POST_WITH_VIDEO;
            case Constants.TYPE_IMAGE:
                return POST_WITH_IMAGE;
            case Constants.TYPE_LOCATION:
                return POST_WITH_LOCATION;
            case Constants.TYPE_TEXT:
                default:
                return POST_WITH_TEXT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {
            case POST_WITH_IMAGE:
                View viewImagePost = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_post_image, parent, false);
                viewHolder = new PostWithImageViewHolder(viewImagePost);
                break;

            case POST_WITH_VIDEO:
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_video, parent, false);
                viewHolder = new PostWithVideoViewHolder(v2);
                break;

            case POST_WITH_LOCATION:
                View v3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_location, parent, false);
                viewHolder = new PostWithLocationViewHolder(v3);
                break;

            case POST_WITH_TEXT:
                default:
                View viewTextPost = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_post_text, parent, false);
                viewHolder = new PostWithTextViewHolder(viewTextPost);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Post post = postList.get(position);
        if (post != null) {
            switch (holder.getItemViewType()) {
                case POST_WITH_IMAGE:
                    break;

                case POST_WITH_VIDEO:

                    break;

                case POST_WITH_LOCATION:

                    break;

                case POST_WITH_TEXT:
                default:
                    PostWithTextViewHolder textVH = (PostWithTextViewHolder) holder;
                    textVH.text.setText(post.getText());
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
