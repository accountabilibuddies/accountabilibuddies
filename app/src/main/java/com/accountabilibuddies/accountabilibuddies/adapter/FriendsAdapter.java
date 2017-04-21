package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.model.Friend;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class FriendsAdapter  extends
        RecyclerView.Adapter<FriendsAdapter.MyViewHolder> {
    protected ArrayList<Friend> friendsList;
    protected Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivFriend)
        CircularImageView profilePic;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public FriendsAdapter(Context context, ArrayList<Friend> friendsList) {
        this.friendsList = friendsList;
        this.context = context;
    }

    @Override
    public FriendsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fb_friends, parent, false);

        return new FriendsAdapter.MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }
}
