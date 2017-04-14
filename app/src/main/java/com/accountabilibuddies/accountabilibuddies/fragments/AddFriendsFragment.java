package com.accountabilibuddies.accountabilibuddies.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.databinding.FragmentAddFriendsBinding;
import com.accountabilibuddies.accountabilibuddies.model.Friend;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.parse.ParseUser;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class AddFriendsFragment extends Fragment {


    private AppCompatActivity listener;
    private FragmentAddFriendsBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof AppCompatActivity) {
            this.listener = (AppCompatActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_friends, parent, false);
        binding.executePendingBindings();

        setUpFriendsView();

        return binding.getRoot();
    }

    private void setUpFriendsView() {

        APIClient.getClient().getFriendsByUserId(
            ParseUser.getCurrentUser().getObjectId(),
            new APIClient.GetFriendsListener() {
                @Override
                public void onSuccess(List<Friend> friends) {

                    List<String> friendNames = new ArrayList(CollectionUtils.collect(friends,
                            (Friend friend) -> {
                                return friend.getName();
                            }
                    ));

                    String[] friendNameArray = friendNames.toArray(new String[0]);

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            listener.getBaseContext(),
                            android.R.layout.simple_dropdown_item_1line,
                            friendNameArray
                    );

                    binding.actvFriends.setAdapter(adapter);
                }

                @Override
                public void onFailure(String errorMessage) {

                }
            });
    }
}
