package com.accountabilibuddies.accountabilibuddies.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.adapter.PostAdapter;
import com.accountabilibuddies.accountabilibuddies.databinding.ActivityPostDetailsBinding;
import com.accountabilibuddies.accountabilibuddies.fragments.PostDetailsFragment;
import com.accountabilibuddies.accountabilibuddies.model.Challenge;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PostDetailsActivity extends AppCompatActivity {

    ActivityPostDetailsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_details);

        String postId = getIntent().getStringExtra("postId");
        String challengeId = getIntent().getStringExtra("challengeId");

        setUpToolbar();

        addChallengeDetails(postId, challengeId);
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpToolbar() {

        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().getDecorView().setBackground(getResources().getDrawable(R.drawable.background));
    }

    private void setUpDetailsFragment(String postId, String challengeDescription) {

        int viewType = getIntent().getIntExtra("viewType", PostAdapter.POST_WITH_TEXT);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.flPostDetails, PostDetailsFragment.newInstance(postId, challengeDescription, viewType));
        ft.commit();
    }

    private void addChallengeDetails(String postId, String challengeId) {

        APIClient.getClient().getChallengeById(challengeId, new APIClient.GetChallengeListener() {

            @Override
            public void onSuccess(Challenge challenge) {
                getSupportActionBar().setTitle(challenge.getName());
                setUpDetailsFragment(postId, challenge.getDescription());
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(PostDetailsActivity.this, "Failed to get challenge " + challengeId, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
