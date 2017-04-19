package com.accountabilibuddies.accountabilibuddies.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.adapter.FriendsAdapter;
import com.accountabilibuddies.accountabilibuddies.application.ParseApplication;
import com.accountabilibuddies.accountabilibuddies.databinding.FragmentFbFriendsBinding;
import com.accountabilibuddies.accountabilibuddies.model.Friend;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;

import java.util.ArrayList;
import java.util.List;

public class FacebookFriendFragment extends Fragment {

    private FragmentFbFriendsBinding binding;
    ArrayList<Friend> mFriendsList;
    FriendsAdapter mAdapter;

    public static FacebookFriendFragment newInstance(String challengeId) {
        FacebookFriendFragment friendsFragment = new FacebookFriendFragment();
        Bundle args = new Bundle();
        args.putString("challengeId", challengeId);
        friendsFragment.setArguments(args);
        return friendsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        //String challengeId = getArguments().getString("challengeId");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fb_friends, parent, false);

        mFriendsList = new ArrayList<>();
        mAdapter = new FriendsAdapter(getContext(), mFriendsList);
        binding.rvFriends.setAdapter(mAdapter);
        binding.rvFriends.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        loadFriends();
        return binding.getRoot();
    }

    private void loadFriends() {
        String username = ParseApplication.getCurrentUser().getUsername();
        APIClient.getClient().getFriendsByUsername(
                username,
                new APIClient.GetFriendsListener() {
                    @Override
                    public void onSuccess(List<Friend> friends) {
                        Log.d("Test", String.valueOf(friends.get(0).get("profileURL")));
                        if (friends != null) {
                            mFriendsList.clear();
                            mFriendsList.addAll(friends);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {

                    }
                }
        );
    }
}
