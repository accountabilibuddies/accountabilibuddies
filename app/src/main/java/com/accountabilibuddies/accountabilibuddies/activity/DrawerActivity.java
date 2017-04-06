package com.accountabilibuddies.accountabilibuddies.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.databinding.ActivityDrawerBinding;
import com.accountabilibuddies.accountabilibuddies.fragments.ChallengesFragment;
import com.accountabilibuddies.accountabilibuddies.fragments.HomeFragment;
import com.accountabilibuddies.accountabilibuddies.fragments.SettingsFragment;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.crashlytics.android.Crashlytics;

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

        //Get client instance
        client = APIClient.getClient();

        //Set toolbar
        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Show create Fragment
            }
        });

        setUpNavigationDrawer();
        setUpNavigationView();

        /* Create dummy challenge & handle response from server to show Success OR Error
        Challenge challenge = new Challenge(Constants.TYPE_SHOWOFF, "Fitness guru challange",
                "30 min challenge for everyday",
                new Date(),new Date(), Constants.FREQUENCY_ALL_WEEK, null, Constants.CATEGORY_FITNESS);
        client.createChallange(challenge);
        */
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

    private void setUpNavigationView() {
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Fragment fragment = null;
                Class fragmentClass = null;

                switch (menuItem.getItemId()) {
                    case R.id.cChallenges:
                        fragmentClass = HomeFragment.class;
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
