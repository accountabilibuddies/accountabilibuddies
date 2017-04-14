package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.accountabilibuddies.accountabilibuddies.util.Constants;
import com.parse.ParseUser;

import java.util.List;

public class OneOnOneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> postList;
    private Context context;
    private final int DATE_TEXT = -1;
    private final int MY_POST_IMAGE = 0;
    private final int FRIEND_POST_IMAGE = 1;
    private final int MY_POST_TEXT = 2;
    private final int FRIEND_POST_TEXT = 3;
    private final int MY_POST_LOCATION = 4;
    private final int FRIEND_POST_LOCATION = 5;
    private final int MY_POST_VIDEO = 6;
    private final int FRIEND_POST_VIDEO = 7;

    public OneOnOneAdapter(Context context, List<Object> postList) {
        this.postList = postList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {

        int type = DATE_TEXT;
        if(postList.get(position) instanceof String) {
            return type;
        }

        Post currentPost = (Post)postList.get(position);

        ParseUser postOwner = currentPost.getOwner();
        ParseUser me = ParseUser.getCurrentUser();

        if(postOwner.getObjectId().equals(me.getObjectId())) {
            type = MY_POST_IMAGE;
            if(currentPost.getType() == Constants.TYPE_TEXT) {
                type = MY_POST_TEXT;
            }
            if(currentPost.getType() == Constants.TYPE_LOCATION) {
                type = MY_POST_LOCATION;
            }
        } else {
            type = FRIEND_POST_IMAGE;
            if(currentPost.getType() == Constants.TYPE_TEXT) {
                type = FRIEND_POST_TEXT;
            }
            if(currentPost.getType() == Constants.TYPE_LOCATION) {
                type = FRIEND_POST_LOCATION;
            }
        }

        return type;
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
                View viewFriendPostText = inflater.inflate(R.layout.item_friend_post_text, parent, false);
                viewHolder = new FriendPostHolderText(viewFriendPostText);
                break;

            case MY_POST_LOCATION:
                View viewMyPostLocation = inflater.inflate(R.layout.item_my_post_map, parent, false);
                viewHolder = new MyPostHolderLocation(viewMyPostLocation);
                break;

            case FRIEND_POST_LOCATION:
                View viewFriendPostLocation = inflater.inflate(R.layout.item_friend_post_map, parent, false);
                viewHolder = new FriendPostHolderLocation(viewFriendPostLocation);
                break;

            case DATE_TEXT:
            default:
                View viewDateText = inflater.inflate(R.layout.item_oneonone_date, parent, false);
                viewHolder = new PostDateHolder(viewDateText);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Object post = postList.get(position);
        if (post != null) {
            switch (holder.getItemViewType()) {
                case MY_POST_IMAGE:
                    MyPostHolderImage myPH = (MyPostHolderImage) holder;
                    myPH.viewBasedOnPost((Post)post,context);
                    break;

                case FRIEND_POST_IMAGE:
                    FriendPostHolderImage friendPH = (FriendPostHolderImage) holder;
                    friendPH.viewBasedOnPost((Post)post,context);
                    break;

                case MY_POST_TEXT:
                    MyPostHolderText myPHText = (MyPostHolderText) holder;
                    myPHText.viewBasedOnPost((Post)post,context);
                    break;

                case FRIEND_POST_TEXT:
                    FriendPostHolderText friendPHText = (FriendPostHolderText) holder;
                    friendPHText.viewBasedOnPost((Post)post,context);
                    break;

                case MY_POST_LOCATION:
                    MyPostHolderLocation myPHLocation = (MyPostHolderLocation) holder;
                    myPHLocation.viewBasedOnPost((Post)post,context);
                    break;

                case FRIEND_POST_LOCATION:
                    FriendPostHolderLocation friendPHLocation = (FriendPostHolderLocation) holder;
                    friendPHLocation.viewBasedOnPost((Post)post,context);
                    break;

                case DATE_TEXT:
                default:
                    PostDateHolder dateHolder = (PostDateHolder) holder;
                    dateHolder.viewBasedOnPost((String)post,context);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
