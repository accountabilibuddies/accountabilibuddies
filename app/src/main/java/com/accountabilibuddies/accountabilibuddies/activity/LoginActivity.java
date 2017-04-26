package com.accountabilibuddies.accountabilibuddies.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    int ONBOARDING_SLIDE_NUM = 3;

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
            finish();
        } else {
            setUpBinding();
            //startLoginAnimation();
            setupViewPager();
            setUpLogInButton(); //user may or may not exist, but isn't authenticated
        }
    }

    private void setupViewPager() {
        binding.container.setAdapter(new CustomPagerAdapter(this));
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
/*
    private void startLoginAnimation() {

        AnimationDrawable animationDrawable = (AnimationDrawable) binding.rlLogin.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
    }
*/
    private void authenticateUser() {

        viewModel.logInWithReadPermissions(new LoginViewModel.LoggedInListener() {

            @Override
            public void onSuccess(boolean isNewUser) {

                ParseApplication.setCurrentUser(ParseUser.getCurrentUser());
                viewModel.createFriendsList();
                openSplashView();
                finish();
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
                openSplashView();
                finish();
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

    private void openSplashView() {

        Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
        startActivity(intent);

    }

    public enum CustomPagerEnum {

        ONE(R.layout.onboarding_1),
        TWO(R.layout.onboarding_2),
        THREE(R.layout.onboarding_3);

        private int mLayoutResId;

        CustomPagerEnum(int layoutResId) {
            mLayoutResId = layoutResId;
        }

        public int getLayoutResId() {
            return mLayoutResId;
        }

    }

    public class CustomPagerAdapter extends PagerAdapter {

        private Context mContext;

        public CustomPagerAdapter(Context context) {
            mContext = context;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
            LayoutInflater inflater = LayoutInflater.from(mContext);
            ViewGroup layout = (ViewGroup) inflater.inflate(customPagerEnum.getLayoutResId(), collection, false);
            collection.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return ONBOARDING_SLIDE_NUM;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
