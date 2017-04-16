package com.accountabilibuddies.accountabilibuddies.viewmodel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.accountabilibuddies.accountabilibuddies.model.Friend;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginViewModel {

    public static final String TAG = LoginViewModel.class.getSimpleName();

    private AppCompatActivity context;

    public interface LoggedInListener {

        void onSuccess(boolean isNewUser);
        void onFailure();
    }

    public LoginViewModel(AppCompatActivity context) {

        this.context = context;
    }

    private void saveFriend(String username, ParseUser friendUser) {

        Friend friend = new Friend();
        friend.setUsername(username);
        friend.setFriend(friendUser);

        APIClient.getClient().createFriend(friend, new APIClient.CreateFriendListener() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "Friend creation success!");
            }

            @Override
            public void onFailure(String errorMessage) {

                Log.d(TAG, "Friend creation failure!");
            }
        });
    }

    public void refreshToken(LoggedInListener listener) {

        AccessToken.refreshCurrentAccessTokenAsync(new AccessToken.AccessTokenRefreshCallback() {
            @Override
            public void OnTokenRefreshed(AccessToken accessToken) {
                listener.onSuccess(false);
            }

            @Override
            public void OnTokenRefreshFailed(FacebookException exception) {
                listener.onFailure();
            }
        });
    }

    public void createFriendsList() {

        ParseUser currentUser = ParseUser.getCurrentUser();

        GraphRequest friendRequest = GraphRequest.newMyFriendsRequest(
            AccessToken.getCurrentAccessToken(),

            (objects,  response) -> {
                Log.d(TAG + "Friends List", response.toString());
                JSONObject resultsJson = response.getJSONObject();

                try {
                    JSONArray dataArray = resultsJson.getJSONArray("data");

                    for (int i = 0; i < dataArray.length(); i++) {

                        JSONObject userData = dataArray.getJSONObject(i);
                        String id = userData. getString("id");

                        APIClient.getClient().getUser(id, new APIClient.UserFoundListener() {

                            @Override
                            public void onSuccess(ParseUser user) {

                                //TODO: add friend to list of friends for the logged-in user
                                addNewFriendRelationship(currentUser, user);
                            }

                            @Override
                            public void onFailure(String errorMessage) {

                                //TODO: user not in ParseUser db create new parse user
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        );

        Bundle params = new Bundle();
        params.putString("fields", "id, name, email, picture, cover");
        friendRequest.setParameters(params);
        friendRequest.executeAsync();
    }

    public boolean addNewFriendRelationship(ParseUser currentUser, ParseUser newFriend) {


        APIClient.getClient().getFriendsByUsername(currentUser.getUsername(), new APIClient.GetFriendsListener() {
            @Override
            public void onSuccess(List<Friend> friends) {

                CollectionUtils.filter(friends,

                    (Friend friend) -> {
                        return friend.getParseUserId().equals(newFriend.getObjectId());
                    }
                );


                if (friends.isEmpty()) {
                    saveFriend(currentUser.getUsername(), newFriend);
                }
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });

        return false;
    }

    public void logInWithReadPermissions(LoggedInListener listener) {

        ParseFacebookUtils.logInWithReadPermissionsInBackground(
            context,
            Arrays.asList("public_profile", "user_friends", "email"),

            (ParseUser user, ParseException err) -> {
                if (user == null) {
                    Log.d(TAG, "Uh oh. The user cancelled the Facebook login.");
                    listener.onFailure();
                } else if (user.isNew()) {
                    Log.d(TAG, "User signed up and logged in through Facebook!");
                    getProfileDataForUser(user);
                    listener.onSuccess(true);
                } else {
                    Log.d(TAG, "User logged in through Facebook!");
                    getProfileDataForUser(user);
                    listener.onSuccess(false);
                }
            }
        );
    }

    public void getProfileDataForUser(ParseUser user) {

        GraphRequest profileDataRequest = GraphRequest.newMeRequest(
            AccessToken.getCurrentAccessToken(),

            (JSONObject data, GraphResponse response) -> {

                Log.d(TAG + "profile data", response.toString());

                if (response.getError() != null) {
                    Log.d(TAG, "Profile data request failed");
                } else {
                    parseProfileData(user, data);
                }
            }
        );

        Bundle params = new Bundle();
        params.putString("fields", "id, name, email, picture, cover");
        profileDataRequest.setParameters(params);
        profileDataRequest.executeAsync();
    }

    private void parseProfileData(ParseUser user, JSONObject data) {

        try {
            String id = data.getString("id");
            String name = data.getString("name");
            String email = data.getString("email");

            JSONObject picture = data.getJSONObject("picture");
            JSONObject pictureData = picture.getJSONObject("data");
            String profilePhotoUrl = pictureData.getString("url");

            if (data.has("cover")) {
                JSONObject cover = data.getJSONObject("cover");
                String coverPhotoUrl = cover.getString("source");
                user.put("coverPhotoUrl", coverPhotoUrl);
            }

            user.setUsername(id);
            user.setEmail(email);

            user.put("facebookId", id);
            user.put("name", name);
            user.put("profilePhotoUrl", profilePhotoUrl);
            user.saveInBackground();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getFriendsForCurrentUser() {

        APIClient.getClient().getFriendsByUsername(
            ParseUser.getCurrentUser().getUsername(),
            new APIClient.GetFriendsListener() {

                @Override
                public void onSuccess(List<Friend> friends) {

                    Log.d(TAG, "Here are my friends: " + friends.toString());
                }

                @Override
                public void onFailure(String errorMessage) {

                    Log.d(TAG, "Error getting friends list.");
                }
            }
        );
    }
}
