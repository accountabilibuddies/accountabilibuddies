
package com.accountabilibuddies.accountabilibuddies.viewmodel;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.accountabilibuddies.accountabilibuddies.model.Friend;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.parse.ParseUser;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class AddFriendsViewModel {

    Context context;
    List<Friend> friends;


    public AddFriendsViewModel(Context context) {

        this.context = context;
    }

    public void addFriend(ParseUser friend) {

        APIClient.getClient().addFriendToChallenge("", friend, new APIClient.AddFriendToChallengeListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "Added friend: " + friend.get("name"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(context, "Failed to add new friend: " + friend.get("name"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showFriendsView(AutoCompleteTextView actvFriends) {

        String username = ParseUser.getCurrentUser().getUsername();

        APIClient.getClient().getFriendsByUsername(
            username,
            new APIClient.GetFriendsListener() {
                @Override
                public void onSuccess(List<Friend> friends) {

                    AddFriendsViewModel.this.friends = friends;

                    List<String> friendNames = new ArrayList(CollectionUtils.collect(friends,
                            (Friend friend) -> {
                                return friend.getName();
                            }
                    ));

                    String[] friendNameArray = friendNames.toArray(new String[0]);

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            context,
                            android.R.layout.simple_dropdown_item_1line,
                            friendNameArray
                    );

                    actvFriends.setAdapter(adapter);
                }

                @Override
                public void onFailure(String errorMessage) {

                }
            }
        );
    }

    public ParseUser getFriend(int position) {

        return friends.get(position).getFriend();
    }
}
