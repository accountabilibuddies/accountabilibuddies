package com.accountabilibuddies.accountabilibuddies.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.databinding.ActivityLoginBinding;
import com.accountabilibuddies.accountabilibuddies.model.Category;
import com.accountabilibuddies.accountabilibuddies.viewmodel.LoginViewModel;
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

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setUpBinding();

        ParseUser user = ParseUser.getCurrentUser();

        if (user == null) {
            setUpLoginButton();
        } else {
            refreshTokenAndGetFriendsList();
            openMainView();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    private void setUpBinding() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLoginViewModel(new LoginViewModel());
    }

    private void setUpNewUser(ParseUser user) {

        List<Category> categories = new ArrayList<>();
        user.put(Category.PLURAL, categories);
        user.saveInBackground();
    }

    private void setUpLoginButton() {

        binding.lbFacebook.setOnClickListener((view) -> {
            logIn();
        });
    }

    private void refreshTokenAndGetFriendsList() {

        AccessToken.refreshCurrentAccessTokenAsync(new AccessToken.AccessTokenRefreshCallback() {
            @Override
            public void OnTokenRefreshed(AccessToken accessToken) {
                getFriendsList(accessToken);
            }

            @Override
            public void OnTokenRefreshFailed(FacebookException exception) {

            }
        });
    }

    private void openMainView() {

        Intent intent = new Intent(LoginActivity.this, DrawerActivity.class);
        startActivity(intent);
    }

    private void logIn() {

        ParseFacebookUtils.logInWithReadPermissionsInBackground(
            this,
            Arrays.asList("public_profile", "user_friends"),

            (ParseUser user, ParseException err) -> {
                if (user == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d("MyApp", "User signed up and logged in through Facebook!");
                    setUpNewUser(user);
                    Intent intent = new Intent(LoginActivity.this, CategoriesActivity.class);
                    startActivity(intent);
                } else {
                    Log.d("MyApp", "User logged in through Facebook!");
                }
            }
        );
    }

    private void getFriendsList(AccessToken token) {

        GraphRequest friendRequest = GraphRequest.newMyFriendsRequest(
            token,

            (objects,  response) -> {
                Log.d("Friends List", response.toString());
                JSONObject resultsJson = response.getJSONObject();

                try {
                    JSONArray resultsArray = resultsJson.getJSONArray("data");

                    if (resultsArray.length() > 0) {
                        JSONObject user = resultsArray.getJSONObject(0);
                        String name = user.getString("name");
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
}
