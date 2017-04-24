package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.view.View;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.model.Friend;
import com.accountabilibuddies.accountabilibuddies.util.ImageUtils;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;

public class ChallengeFriendsAdapter extends FriendsAdapter {

    ArrayList<ParseUser> userList;

    public ChallengeFriendsAdapter(Context context, ArrayList<Friend> friendsList, ArrayList<ParseUser> userList) {
        super(context, friendsList);
        this.userList = userList;
    }

    private boolean isFriendInChallenge(ParseUser friend) {
        for(ParseUser user : userList) {
            if(user.getObjectId().equals(friend.getObjectId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBindViewHolder(FriendsAdapter.MyViewHolder holder, int position) {
        Friend friend = friendsList.get(position);
        holder.profilePic.setImageResource(0);
        holder.select.setVisibility(View.GONE);
        holder.profilePic.setShadowColor(context.getResources().getColor(R.color.grey2));
        if (friend != null) {

            ImageUtils.loadProfileImage(
                context,
                friend.getProfileURL(),
                holder.profilePic
            );

            try {
                holder.name.setText(friend.getFriend().fetchIfNeeded().getString("name"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (isFriendInChallenge(friend.getFriend())) {
                holder.profilePic.setShadowColor(context.getResources().getColor(R.color.colorAccent));
                holder.select.setVisibility(View.VISIBLE);
            }
        }
    }

    public void add(ParseUser friend) {
        userList.add(friend);
        notifyDataSetChanged();
    }

    public void remove(ParseUser friend) {
        CollectionUtils.filter(
                userList,
                (ParseUser user) -> {
                    return !user.getObjectId().equals(friend.getObjectId());
                }
        );
        notifyDataSetChanged();
    }
}
