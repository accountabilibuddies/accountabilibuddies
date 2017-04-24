package com.accountabilibuddies.accountabilibuddies.viewmodel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.accountabilibuddies.accountabilibuddies.application.ParseApplication;
import com.accountabilibuddies.accountabilibuddies.model.Friend;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class LoginViewModel {

    public static final String TAG = LoginViewModel.class.getSimpleName();

    private AppCompatActivity context;

    public interface LoggedInListener {

        void onSuccess();
        void onFailure();
    }

    public LoginViewModel(AppCompatActivity context) {

        this.context = context;
    }

    private void saveFriend(String username, ParseUser friendUser) {

        Friend friend = new Friend();
        friend.setUsername(username);
        friend.setProfileURL((String) friendUser.get("profilePhotoUrl"));
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

    public void createFriendsList() {

        ParseUser currentUser = ParseApplication.getCurrentUser();

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
                            public void onFailure() {

                                //TODO: user not in ParseUser db create new parse user - shouldn't happen if user already authorized
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        );

        Bundle params = new Bundle();
        params.putString("fields", "id, name, email, cover, picture.type(large)");
        friendRequest.setParameters(params);
        friendRequest.executeAsync();
    }

    public boolean addNewFriendRelationship(ParseUser currentUser, ParseUser newFriend) {

        APIClient.getClient().getFriendsByUsername(currentUser.getUsername(), new APIClient.GetFriendsListener() {
            @Override
            public void onSuccess(List<Friend> friends) {

                CollectionUtils.filter(friends,

                    (Friend friend) -> {

                        String friendId = friend.getParseUserId();
                        String newFriendId = newFriend.getObjectId();

                        if (friendId != null && newFriendId != null) {
                            return friendId.equals(newFriendId);
                        };

                        return false;
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
        params.putString("fields", "id, name, email, cover, picture.type(large)");
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
                ParseApplication.getCurrentUser().getUsername(),
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

    public void setUpLoginCallback(LoginButton btFacebook, CallbackManager callbackManager, LoggedInListener listener) {

        btFacebook.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email"));

        btFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            private ProfileTracker profileTracker;

            @Override
            public void onSuccess(LoginResult loginResult) {

                if (Profile.getCurrentProfile() == null) {
                    profileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                            Log.d(TAG, currentProfile.toString());
                            getUser(currentProfile, listener);

                            profileTracker.stopTracking();
                        }
                    };
                } else {

                    Profile profile = Profile.getCurrentProfile();
                    Log.d(TAG, profile.toString());
                }
            }

            @Override
            public void onCancel() {

                Log.d(TAG, "canceled");
                listener.onFailure();
            }

            @Override
            public void onError(FacebookException error) {

                Log.d(TAG, "error");
                listener.onFailure();
            }
        });
    }

    public void getUser(Profile profile, LoggedInListener listener) {

        APIClient.getClient().getUser(profile.getId(), new APIClient.UserFoundListener() {
            @Override
            public void onSuccess(ParseUser user) {
                ParseApplication.setCurrentUser(user);
                getProfileDataForUser(user);
                createFriendsList();
                listener.onSuccess();
            }

            @Override
            public void onFailure() {
                setUpNewUser(profile, listener);
            }
        });

    }

    private void setUpNewUser(Profile profile, LoggedInListener listener) {

        ParseUser newUser = new ParseUser();
        newUser.setUsername(profile.getId());
        newUser.setPassword("default");

        getProfileDataForUser(newUser);
        createFriendsList();
        
        ParseApplication.setCurrentUser(newUser);

        newUser.signUpInBackground(
            (ParseException signUpException) -> {

                ParseFacebookUtils.linkInBackground(

                    newUser,
                    AccessToken.getCurrentAccessToken(),
                    (ParseException saveException) -> {
                        Log.d(TAG, "Done saving " + newUser.toString());
                        listener.onSuccess();
                    }
                );
            }
        );
    }
}
