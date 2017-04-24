package com.accountabilibuddies.accountabilibuddies.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.application.ParseApplication;
import com.accountabilibuddies.accountabilibuddies.databinding.ActivityLoginBinding;
import com.accountabilibuddies.accountabilibuddies.util.ViewUtils;
import com.accountabilibuddies.accountabilibuddies.viewmodel.LoginViewModel;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        viewModel = new LoginViewModel(LoginActivity.this);

        ViewUtils.makeViewFullScreen(getWindow());

        ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser != null) {
            ParseApplication.setCurrentUser(currentUser);
            loadAuthenticatedUser();
            openSplashView();
        } else {
            setUpBinding();
            startLoginAnimation();
            setUpLogInButton(); //user may or may not exist, but isn't authenticated
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void setUpLogInButton() {

        binding.btFacebook.setOnClickListener(
            (View view) -> {
                authenticateUser();
            }
        );
    }

    private void startLoginAnimation() {

        AnimationDrawable animationDrawable = (AnimationDrawable) binding.rlLogin.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
    }

    private void authenticateUser() {

        viewModel.logInWithReadPermissions(new LoginViewModel.LoggedInListener() {

            @Override
            public void onSuccess(boolean isNewUser) {

                ParseApplication.setCurrentUser(ParseUser.getCurrentUser());
                viewModel.createFriendsList();

                openMainView();
                openSplashView();
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

    private void openSplashView() {

        Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
        startActivity(intent);
    }
}
