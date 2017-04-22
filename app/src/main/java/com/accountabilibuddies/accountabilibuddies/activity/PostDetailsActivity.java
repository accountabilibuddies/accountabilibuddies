package com.accountabilibuddies.accountabilibuddies.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.adapter.PostAdapter;
import com.accountabilibuddies.accountabilibuddies.databinding.ActivityPostDetailsBinding;
import com.accountabilibuddies.accountabilibuddies.fragments.PostDetailsFragment;
import com.accountabilibuddies.accountabilibuddies.util.ViewUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PostDetailsActivity extends AppCompatActivity {

    ActivityPostDetailsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_details);

        ViewUtils.makeViewFullScreen(getWindow());

        String postId = getIntent().getStringExtra("postId");
        String challengeId = getIntent().getStringExtra("challengeId");

        setUpDetailsFragment(postId, challengeId);
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void setUpDetailsFragment(String postId, String challengeId) {

        int viewType = getIntent().getIntExtra("viewType", PostAdapter.POST_WITH_TEXT);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.flPostDetails, PostDetailsFragment.newInstance(postId, challengeId, viewType));
        ft.commit();
    }
}
