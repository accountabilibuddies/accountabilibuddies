package com.accountabilibuddies.accountabilibuddies.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.application.ParseApplication;
import com.accountabilibuddies.accountabilibuddies.databinding.ActivityDrawerBinding;
import com.accountabilibuddies.accountabilibuddies.fragments.CompleteChallenges;
import com.accountabilibuddies.accountabilibuddies.fragments.CurrentChallenges;
import com.accountabilibuddies.accountabilibuddies.fragments.UpcomingChallenges;
import com.accountabilibuddies.accountabilibuddies.util.AnimUtils;
import com.accountabilibuddies.accountabilibuddies.util.ImageUtils;
import com.crashlytics.android.Crashlytics;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseUser;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DrawerActivity extends AppCompatActivity {

    private ActivityDrawerBinding binding;
    private ActionBarDrawerToggle mDrawerToggle;
    ParseUser currentUser = null;
    boolean pendingIntroAnimation = false;
    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drawer);

        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        }

        getWindow().getDecorView().setBackground(getResources().getDrawable(R.drawable.background));

        String objectId = null;
        try {
            currentUser = ParseUser.getCurrentUser().fetch();
            objectId = currentUser.fetchIfNeeded().getObjectId();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        final String CHANNEL_NAME = objectId;
        if(CHANNEL_NAME !=null) {
            ParsePush.subscribeInBackground(CHANNEL_NAME);
        } else {
            //TODO: Log error in subscribing
        }

        setSupportActionBar(binding.toolbar);

        setUpNavigationDrawer();
        setUpNavigationView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
        //    startIntroAnimation();
        }
        return true;
    }

    private void startIntroAnimation() {
        binding.fab.setTranslationY(4 * 56);
        int actionbarSize = AnimUtils.dpToPx(56);
        binding.toolbar.setTranslationY(-actionbarSize);
        binding.toolbar.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300)
                .setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startContentAnimation();
            }
        }).start();
    }

    private void startContentAnimation() {
        binding.fab.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(300)
                .setDuration(ANIM_DURATION_FAB)
                .start();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CurrentChallenges fragment = (CurrentChallenges)getSupportFragmentManager().findFragmentByTag("current");
                fragment.loadChallenges();
            }
        }, 1000);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void setUpNavigationDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                binding.toolbar,
                R.string.app_name,
                R.string.app_name
        );

        View headerLayout = binding.navView.getHeaderView(0);

        ImageView ivProfileImage = (ImageView) headerLayout.findViewById(R.id.ivProfileImage);

        ImageUtils.loadProfileImage(
            this,
            currentUser.getString("profilePhotoUrl"),
            ivProfileImage
        );

        TextView tvName = (TextView) headerLayout.findViewById(R.id.tvName);
        tvName.setText(currentUser.getString("name"));

        TextView tvEmail = (TextView) headerLayout.findViewById(R.id.tvEmail);
        tvEmail.setText(currentUser.getString("email"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private void setUpNavigationView() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null)
            fragmentManager.beginTransaction().replace(R.id.frame, new CurrentChallenges(), "current").commit();

        binding.navView.setNavigationItemSelectedListener(menuItem -> {
            Fragment fragment = null;
            Class fragmentClass = null;

            switch (menuItem.getItemId()) {
                case R.id.cChallenges:
                    fragmentClass = CurrentChallenges.class;
                    break;

                case R.id.uChallenges:
                    fragmentClass = UpcomingChallenges.class;
                    break;

                case R.id.pChallenges:
                    fragmentClass = CompleteChallenges.class;
                    break;

                case R.id.logOut:
                    ParseUser.logOutInBackground(
                        (ParseException e) -> {
                            ParseApplication.setCurrentUser(null);
                            openLoginView();
                        }
                    );
                    break;
                case R.id.settings:
                case R.id.help:
                case R.id.about:
                    return true;
            }

            try {
                if (fragmentClass != null) {
                    fragment = (Fragment) fragmentClass.newInstance();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (fragmentManager != null && fragment != null)
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();

            menuItem.setChecked(true);
            setTitle(menuItem.getTitle());
            binding.drawerLayout.closeDrawers();
            return true;
        });
    }

    private void openLoginView() {

        Intent intent = new Intent(DrawerActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void createChallenge(View view) {
        Intent i = new Intent(this, CreateChallengeActivity.class);
        startActivity(i);
    }
}
