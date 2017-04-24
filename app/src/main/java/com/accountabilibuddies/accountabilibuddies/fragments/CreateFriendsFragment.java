package com.accountabilibuddies.accountabilibuddies.fragments;

import com.accountabilibuddies.accountabilibuddies.adapter.CreateFriendsAdapter;
import com.parse.ParseUser;

import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

public class CreateFriendsFragment extends FriendFragment {

    Set<ParseUser> selectedFriends = new HashSet<>();
    CreateFriendsAdapter mAdapter;


    void CreateFriendsFragment(){}

    public Set<ParseUser> getSelectedFriends() {
        return selectedFriends;
    }

    public void addFriend(ParseUser friend) {
        selectedFriends.add(friend);
        mAdapter.setSelectedList(selectedFriends);
    }

    public void removeFriend(ParseUser friend) {
        CollectionUtils.filter(
                selectedFriends,
                (ParseUser user) -> {
                    return !user.getObjectId().equals(friend.getObjectId());
                }
        );
        mAdapter.setSelectedList(selectedFriends);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void setAdapter() {
        mAdapter = new CreateFriendsAdapter(getContext(), mFriendsList);
        binding.rvFriends.setAdapter(mAdapter);
    }

    @Override
    protected void onClick(int position) {
        ParseUser friend = mFriendsList.get(position).getFriend();
        if (selectedFriends.contains(friend))
            removeFriend(friend);
        else
            addFriend(friend);
    }

    @Override
    protected void updateAdapter() {
        mAdapter.notifyDataSetChanged();
    }
}
