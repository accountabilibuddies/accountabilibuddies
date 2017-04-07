package com.accountabilibuddies.accountabilibuddies.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.accountabilibuddies.accountabilibuddies.LoginViewModel;
import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.databinding.ActivityLoginBinding;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setUpBinding();

        AccessToken token = AccessToken.getCurrentAccessToken();

        if (token == null) {
            setUpLoginButton();
        } else {
            getFriendsList(token);
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

    private void setUpLoginButton() {

        binding.lbFacebook.setOnClickListener((view) -> {
            logIn();
        });
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
                Log.d("FriendsList", response.toString());
                JSONObject resultsJson = response.getJSONObject();

                try {
                    JSONArray resultsArray = resultsJson.getJSONArray("data");
                    JSONObject user = resultsArray.getJSONObject(0);
                    String name = user.getString("name");
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
