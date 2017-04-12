package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.parse.ParseUser;

import java.util.List;

public class OneOnOneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Post> postList;
    private Context context;
    private final int MY_POST_IMAGE = 0;
    private final int FRIEND_POST_IMAGE = 1;
    private final int MY_POST_TEXT = 2;
    private final int FRIEND_POST_TEXT = 3;

    public OneOnOneAdapter(Context context, List<Post> postList) {
        this.postList = postList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {

        //TODO: Improve this code
        int type = 1;
        Post currentPost = postList.get(position);

        ParseUser postOwner = currentPost.getOwner();
        ParseUser me = ParseUser.getCurrentUser();
        if(postOwner.getObjectId().equals(me.getObjectId())) {
            type = 0;
            if(currentPost.getText()!=null) {
                type = 2;
            }
        } else {
            if(currentPost.getText()!=null) {
                type = 3;
            }
        }

        switch (type) {
            case 0:
                return MY_POST_IMAGE;
            case 1:
                return FRIEND_POST_IMAGE;
            case 2:
                return MY_POST_TEXT;
            case 3:
            default:
                return FRIEND_POST_TEXT;

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case MY_POST_IMAGE:
                View viewMyPostImage = inflater.inflate(R.layout.item_my_post_image, parent, false);
                viewHolder = new MyPostHolderImage(viewMyPostImage);
                break;

            case FRIEND_POST_IMAGE:
                View viewFriendPostImage = inflater.inflate(R.layout.item_friend_post_image, parent, false);
                viewHolder = new FriendPostHolderImage(viewFriendPostImage);
                break;

            case MY_POST_TEXT:
                View viewMyPostText = inflater.inflate(R.layout.item_my_post_text, parent, false);
                viewHolder = new MyPostHolderText(viewMyPostText);
                break;

            case FRIEND_POST_TEXT:
            default:
                View viewFriendPostText = inflater.inflate(R.layout.item_friend_post_text, parent, false);
                viewHolder = new FriendPostHolderText(viewFriendPostText);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Post post = postList.get(position);
        if (post != null) {
            switch (holder.getItemViewType()) {
                case MY_POST_IMAGE:
                    MyPostHolderImage myPH = (MyPostHolderImage) holder;
                    myPH.viewBasedOnPost(post,context);
                    break;

                case FRIEND_POST_IMAGE:
                    FriendPostHolderImage friendPH = (FriendPostHolderImage) holder;
                    friendPH.viewBasedOnPost(post,context);
                    break;

                case MY_POST_TEXT:
                    MyPostHolderText myPHText = (MyPostHolderText) holder;
                    myPHText.viewBasedOnPost(post,context);
                    break;

                case FRIEND_POST_TEXT:
                default:
                    FriendPostHolderText friendPHText = (FriendPostHolderText) holder;
                    friendPHText.viewBasedOnPost(post,context);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
