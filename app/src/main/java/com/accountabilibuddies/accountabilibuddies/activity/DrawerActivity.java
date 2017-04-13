package com.accountabilibuddies.accountabilibuddies.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.databinding.ActivityDrawerBinding;
import com.accountabilibuddies.accountabilibuddies.fragments.CategoryFilterChallenges;
import com.accountabilibuddies.accountabilibuddies.fragments.CreateChallengeFragment;
import com.accountabilibuddies.accountabilibuddies.fragments.CurrentChallenges;
import com.accountabilibuddies.accountabilibuddies.fragments.SettingsFragment;
import com.accountabilibuddies.accountabilibuddies.fragments.UpcomingChallenges;
import com.accountabilibuddies.accountabilibuddies.model.Challenge;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.crashlytics.android.Crashlytics;
import com.parse.ParsePush;
import com.parse.ParseUser;

import java.util.List;

import io.fabric.sdk.android.Fabric;

public class DrawerActivity extends AppCompatActivity {

    private ActivityDrawerBinding binding;
    private ActionBarDrawerToggle mDrawerToggle;
    private APIClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drawer);

        final String CHANNEL_NAME = "beaccountable";
        ParsePush.subscribeInBackground(CHANNEL_NAME);

        client = APIClient.getClient();
        setSupportActionBar(binding.toolbar);

        setUpNavigationDrawer();
        setUpNavigationView();
    }

    private void setUpNavigationDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                binding.toolbar,
                R.string.app_name,
                R.string.app_name
        );
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
            fragmentManager.beginTransaction().replace(R.id.frame, new CurrentChallenges()).commit();

        /*
        //Get Challenges user has joined and display else display challenges as per his categories
        client.getChallengeList(ParseUser.getCurrentUser(), new APIClient.GetChallengeListListener(){
            @Override
            public void onSuccess(List<Challenge> challengeList) {
                Fragment fragment;
                if (challengeList.size() > 0) {
                    fragment = new CurrentChallenges();
                } else {
                    fragment = new CategoryFilterChallenges();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager != null)
                    fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
            }

            @Override
            public void onFailure(String error_message) {
                Snackbar.make(binding.clayout, error_message, Snackbar.LENGTH_LONG).show();
            }
        });
        */
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

                case R.id.sChallenges:
                    fragmentClass = CategoryFilterChallenges.class;
                    break;

                case R.id.settings:
                    fragmentClass = SettingsFragment.class;
                    break;

                case R.id.help:
                case R.id.about:
                case R.id.logOut:
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

    public void createChallenge(View view) {
        //Will reomve this cod later once code is moved from Fragment to activity
        CreateChallengeFragment createChallengeFragment = new CreateChallengeFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null)
            fragmentManager.beginTransaction().replace(R.id.frame, createChallengeFragment).
                    addToBackStack(null).commit();
    }
}
