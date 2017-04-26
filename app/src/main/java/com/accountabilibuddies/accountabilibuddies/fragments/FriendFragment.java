package com.accountabilibuddies.accountabilibuddies.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.databinding.FragmentFbFriendsBinding;
import com.accountabilibuddies.accountabilibuddies.model.Friend;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.accountabilibuddies.accountabilibuddies.util.ItemClickSupport;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public abstract class FriendFragment extends Fragment {

    protected FragmentFbFriendsBinding binding;
    ArrayList<Friend> mFriendsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fb_friends, parent, false);

        mFriendsList = new ArrayList<>();

        setAdapter();

        binding.rvFriends.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        //handle click event
        ItemClickSupport.addTo(binding.rvFriends)
                .setOnItemClickListener((recyclerView, position, v) -> onClick(position));
        loadFriends();
        return binding.getRoot();
    }

    protected abstract void setAdapter();

    protected abstract void onClick(int position);

    private void loadFriends() {
        String username = ParseUser.getCurrentUser().getUsername();
        APIClient.getClient().getFriendsByUsername(
                username,
                new APIClient.GetFriendsListener() {
                    @Override
                    public void onSuccess(List<Friend> friends) {
                        if (friends != null) {
                            mFriendsList.clear();
                            mFriendsList.addAll(friends);
                            updateAdapter();
                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {

                    }
                }
        );
    }

    protected abstract void updateAdapter();
}
