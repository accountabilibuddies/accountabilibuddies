package com.accountabilibuddies.accountabilibuddies.viewmodel;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.accountabilibuddies.accountabilibuddies.model.Friend;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.parse.ParseUser;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class AddFriendsViewModel {

    Context context;

    public AddFriendsViewModel(Context context) {

        this.context = context;
    }

    public void showFriendsView(AutoCompleteTextView actvFriends) {

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
}
