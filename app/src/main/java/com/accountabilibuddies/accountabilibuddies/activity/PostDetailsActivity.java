package com.accountabilibuddies.accountabilibuddies.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.adapter.PostAdapter;
import com.accountabilibuddies.accountabilibuddies.databinding.ActivityPostDetailsBinding;
import com.accountabilibuddies.accountabilibuddies.fragments.PostDetailsFragment;

public class PostDetailsActivity extends AppCompatActivity {

    ActivityPostDetailsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_details);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        String postId = getIntent().getStringExtra("postId");

        setUpDetailsFragment(postId);
    }

    private void setUpDetailsFragment(String postId) {

        int viewType = getIntent().getIntExtra("viewType", PostAdapter.POST_WITH_TEXT);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.flPostDetails, PostDetailsFragment.newInstance(postId, viewType));
        ft.commit();
    }
}
