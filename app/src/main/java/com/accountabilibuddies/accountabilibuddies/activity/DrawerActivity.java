package com.accountabilibuddies.accountabilibuddies.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.databinding.ActivityDrawerBinding;
import com.accountabilibuddies.accountabilibuddies.fragments.CategoryFilterChallenges;
import com.accountabilibuddies.accountabilibuddies.fragments.ChallengesFragment;
import com.accountabilibuddies.accountabilibuddies.fragments.CreateChallengeFragment;
import com.accountabilibuddies.accountabilibuddies.fragments.CurrentChallenges;
import com.accountabilibuddies.accountabilibuddies.fragments.SettingsFragment;
import com.accountabilibuddies.accountabilibuddies.modal.Challenge;
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

        //Get client instance
        client = APIClient.getClient();

        //Set toolbar
        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(view -> {
            //Show create Fragment
            CreateChallengeFragment createChallengeFragment = new CreateChallengeFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, createChallengeFragment).commit();


        });

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
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled
        // the nav drawer indicator touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private void showCurrentChallenges() {
        Fragment fragment = new CurrentChallenges();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
    }

    private void showCategoryChallenegs() {
        Fragment fragment = new CategoryFilterChallenges();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
    }

    private void setUpNavigationView() {

        //Get challanges user has joined and display else display challenges as per his categories
        client.getChallengeList(ParseUser.getCurrentUser(), new APIClient.GetChallengeListListener(){
            @Override
            public void onSuccess(List<Challenge> challengeList) {
                if (challengeList.size() > 0) {
                    showCurrentChallenges();
                } else {
                    showCategoryChallenegs();
                }
            }

            @Override
            public void onFailure(String error_message) {
                Snackbar.make(binding.clayout, error_message, Snackbar.LENGTH_LONG).show();
            }
        });

        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                Fragment fragment = null;
                Class fragmentClass = null;

                switch (menuItem.getItemId()) {
                    case R.id.cChallenges:
                        fragmentClass = CurrentChallenges.class;
                        break;

                    case R.id.uChallenges:
                        fragmentClass = ChallengesFragment.class;
                        break;

                    case R.id.sChallenges:
                        fragmentClass = ChallengesFragment.class;
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
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();

                // Highlight the selected item has been done by NavigationView
                menuItem.setChecked(true);
                // Set action bar title
                setTitle(menuItem.getTitle());
                binding.drawerLayout.closeDrawers();
                return true;
            }
        });
    }
}
