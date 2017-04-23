package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.model.Friend;
import com.accountabilibuddies.accountabilibuddies.util.ImageUtils;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CreateFriendsAdapter extends FriendsAdapter {

    Set<ParseUser> selectedUserList = new HashSet<>();

    public CreateFriendsAdapter(Context context, ArrayList<Friend> friendsList) {
        super(context, friendsList);
    }

    private boolean isFriendAdded(ParseUser friend) {
        for(ParseUser user : selectedUserList) {
            if(user.getObjectId().equals(friend.getObjectId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Friend friend = friendsList.get(position);

        if (friend != null) {

            ImageUtils.loadImage(
                context,
                friend.getProfileURL(),
                holder.profilePic
            );

            try {
                holder.name.setText(friend.getFriend().fetchIfNeeded().getString("name"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (!selectedUserList.isEmpty() &&
                    isFriendAdded(friend.getFriend())) {
                holder.profilePic.setShadowColor(context.getResources().getColor(R.color.colorAccent));
            }
        }
    }

    public void setSelectedList(Set<ParseUser> selectedUserList) {
        this.selectedUserList = selectedUserList;
        notifyDataSetChanged();
    }
}
