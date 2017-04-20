package com.accountabilibuddies.accountabilibuddies.fragments;

import android.os.Bundle;

import com.accountabilibuddies.accountabilibuddies.adapter.ChallengeFriendsAdapter;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ChallengeFriendsFragment extends FriendFragment {

    ArrayList<ParseUser> userList = new ArrayList<>();
    ChallengeFriendsAdapter mAdapter;

    public static ChallengeFriendsFragment newInstance(String challengeId) {
        ChallengeFriendsFragment friendsFragment = new ChallengeFriendsFragment();
        Bundle args = new Bundle();
        args.putString("challengeId", challengeId);
        friendsFragment.setArguments(args);
        return friendsFragment;
    }

    @Override
    protected void setAdapter() {
        String challengeId = getArguments().getString("challengeId");

        APIClient.getClient().getMembersList(challengeId, new APIClient.GetMembersListListener() {
            @Override
            public void onSuccess(List<ParseUser> membersList) {
                if (membersList != null) {
                    userList.addAll(membersList);
                }
            }

            @Override
            public void onFailure(String error_message) {}
        });
        mAdapter = new ChallengeFriendsAdapter(getContext(), mFriendsList, userList);
        binding.rvFriends.setAdapter(mAdapter);
    }

    @Override
    protected void onClick(int position) {
        ParseUser friend = mFriendsList.get(position).getFriend();
        APIClient.getClient().addRemoveFriendToChallenge(getArguments().getString("challengeId"),
                friend, new APIClient.AddFriendToChallengeListener() {
            @Override
            public void onSuccess(String status) {
                if(status.equals("add")) {
                    mAdapter.add(friend);
                } else  if (status.equals("remove"))
                    mAdapter.remove(friend);
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }

    @Override
    protected void updateAdapter() {
        mAdapter.notifyDataSetChanged();
    }
}
