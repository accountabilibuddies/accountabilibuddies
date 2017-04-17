package com.accountabilibuddies.accountabilibuddies.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.application.ParseApplication;
import com.accountabilibuddies.accountabilibuddies.databinding.ActivityLoginBinding;
import com.accountabilibuddies.accountabilibuddies.viewmodel.LoginViewModel;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        viewModel = new LoginViewModel(LoginActivity.this);
        setUpBinding();

        ParseUser currentUser = ParseApplication.getCurrentUser();

        if (currentUser != null) {
            ParseApplication.setCurrentUser(currentUser);
            loadAuthenticatedUser();
        }

        setUpLogInButton(); //user may or may not exist, but isn't authenticated
    }

    private void setUpLogInButton() {

        binding.btFacebook.setOnClickListener(
            (View view) -> {
                authenticateUser();
            }
        );
    }

    private void authenticateUser() {

        viewModel.logInWithReadPermissions(new LoginViewModel.LoggedInListener() {

            @Override
            public void onSuccess(boolean isNewUser) {

                ParseApplication.setCurrentUser(ParseUser.getCurrentUser());
                viewModel.createFriendsList();

                openMainView();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void loadAuthenticatedUser() {

        viewModel.refreshToken(new LoginViewModel.LoggedInListener() {
            @Override
            public void onSuccess(boolean isNewUser) {

                viewModel.getFriendsForCurrentUser();
                openMainView();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    private void setUpBinding() {

        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        binding.setLoginViewModel(viewModel);
    }

    private void openMainView() {
        Intent intent = new Intent(LoginActivity.this, DrawerActivity.class);
        startActivity(intent);
        finish();
    }
}
