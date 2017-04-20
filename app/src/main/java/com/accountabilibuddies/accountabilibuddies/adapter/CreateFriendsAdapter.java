package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;

import com.accountabilibuddies.accountabilibuddies.model.Friend;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CreateFriendsAdapter extends FriendsAdapter {
    public CreateFriendsAdapter(Context context, ArrayList<Friend> friendsList) {
        super(context, friendsList);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Friend friend = friendsList.get(position);

        if (friend != null) {
            Glide.with(context)
                    .load(friend.get("profileURL"))
                    .into(holder.profilePic);
        }
    }
}
