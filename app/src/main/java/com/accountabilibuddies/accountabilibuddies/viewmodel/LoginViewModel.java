package com.accountabilibuddies.accountabilibuddies.viewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.accountabilibuddies.accountabilibuddies.activity.CategoriesActivity;
import com.accountabilibuddies.accountabilibuddies.activity.DrawerActivity;
import com.accountabilibuddies.accountabilibuddies.model.Category;
import com.accountabilibuddies.accountabilibuddies.model.Friend;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginViewModel {

    private AppCompatActivity context;

    public LoginViewModel(AppCompatActivity context) {

        this.context = context;
    }

    public void refreshTokenAndGetFriendsList() {

        AccessToken.refreshCurrentAccessTokenAsync(new AccessToken.AccessTokenRefreshCallback() {
            @Override
            public void OnTokenRefreshed(AccessToken accessToken) {
                getFriendsList();
            }

            @Override
            public void OnTokenRefreshFailed(FacebookException exception) {

            }
        });
    }

    private void openCategoriesView() {
        Intent intent = new Intent(context, CategoriesActivity.class);
        context.startActivity(intent);
        context.finish();
    }

    private void setUpNewUser(ParseUser user) {

        List<Category> categories = new ArrayList<>();
        user.put(Category.PLURAL, categories);
        user.saveInBackground();
    }

    public void openMainView() {
        Intent intent = new Intent(context, DrawerActivity.class);
        context.startActivity(intent);
        context.finish();
    }

    private void saveFriend(String facebookId, String name, String photoUrl) {

        Friend friend = new Friend();

        friend.setFacebookId(facebookId);
        friend.setName(name);
        friend.setPhotoUrl(photoUrl);
        friend.setFriendOfId(ParseUser.getCurrentUser().getObjectId());

        APIClient.getClient().createFriend(friend, new APIClient.CreateFriendListener() {

            @Override
            public void onSuccess() {
                Log.d("Login", "Friend creation success!");
            }

            @Override
            public void onFailure(String errorMessage) {

                Log.d("Login", "Friend creation failure!");
            }
        });
    }

    private void getFriendsList() {

        GraphRequest friendRequest = GraphRequest.newMyFriendsRequest(
                AccessToken.getCurrentAccessToken(),

                (objects,  response) -> {
                    Log.d("Friends List", response.toString());
                    JSONObject resultsJson = response.getJSONObject();

                    try {
                        JSONArray resultsArray = resultsJson.getJSONArray("data");

                        for (int i = 0; i < resultsArray.length(); i++) {

                            JSONObject user = resultsArray.getJSONObject(i);
                            JSONObject picture = user.getJSONObject("picture");
                            JSONObject pictureData = picture.getJSONObject("data");

                            saveFriend(
                                user.getString("id"),
                                user.getString("name"),
                                pictureData.getString("url")
                            );
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        );

        Bundle params = new Bundle();
        params.putString("fields", "id, name, picture");
        friendRequest.setParameters(params);
        friendRequest.executeAsync();
    }

    public void logIn(View view) {

        logInWithReadPermissions();
    }

    public void logInWithReadPermissions() {

        ParseFacebookUtils.logInWithReadPermissionsInBackground(
            context,
            Arrays.asList("public_profile", "user_friends"),

            (ParseUser user, ParseException err) -> {
                if (user == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d("MyApp", "User signed up and logged in through Facebook!");
                    setUpNewUser(user);
                    getFriendsList();
                    openCategoriesView();
                } else {
                    Log.d("MyApp", "User logged in through Facebook!");
                    getFriendsList();
                    openMainView();
                }
            }
        );
    }
}
