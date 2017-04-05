package com.accountabilibuddies.accountabilibuddies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.accountabilibuddies.accountabilibuddies.databinding.ActivityLoginBinding;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setUpBinding();
        setUpLoginButton();
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

            //TODO: Add logic for determining whether user is already logged in.
            //TODO: Add logic for setting current user of application
            logIn();
        });
    }

    private void logIn() {

        ParseFacebookUtils.logInWithReadPermissionsInBackground(
            this,
            Arrays.asList("public_profile"),

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
}
