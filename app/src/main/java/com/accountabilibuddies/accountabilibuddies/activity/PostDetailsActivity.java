package com.accountabilibuddies.accountabilibuddies.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.adapter.PostAdapter;
import com.accountabilibuddies.accountabilibuddies.databinding.ActivityPostDetailsBinding;
import com.accountabilibuddies.accountabilibuddies.fragments.PostWithImageFragment;
import com.accountabilibuddies.accountabilibuddies.fragments.PostWithLocationFragment;
import com.accountabilibuddies.accountabilibuddies.fragments.PostWithTextFragment;
import com.accountabilibuddies.accountabilibuddies.fragments.PostWithVideoFragment;
import com.accountabilibuddies.accountabilibuddies.util.ImageUtils;
import com.like.LikeButton;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PostDetailsActivity extends AppCompatActivity {

    ActivityPostDetailsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_details);

        String postId = getIntent().getStringExtra("postId");
        String createdAt = getIntent().getStringExtra("createdAt");

        String ownerName = getIntent().getStringExtra("user");
        String profileImage = getIntent().getStringExtra("profileImage");
        int viewType = getIntent().getIntExtra("viewType",-1);

        setUpToolbar();
        showUserDetails(ownerName, profileImage);

        binding.tvPostTime.setText(createdAt);

        showPost(postId, viewType);

        setUpCommentButton(postId);

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

    private void showUserDetails(String ownerName, String profileImage) {

        binding.pdName.setText(ownerName);

        ImageUtils.loadCircularProfileImage(this, profileImage, binding.ivPDAvatar);
    }

    private void showPost(String postId, int viewType) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (viewType) {
            case PostAdapter.POST_WITH_IMAGE:
                ft.replace(R.id.flPost, PostWithImageFragment.newInstance(postId));
                break;

            case PostAdapter.POST_WITH_VIDEO:
                ft.replace(R.id.flPost, PostWithVideoFragment.newInstance(postId));
                break;

            case PostAdapter.POST_WITH_LOCATION:
                ft.replace(R.id.flPost, PostWithLocationFragment.newInstance(postId));
                break;

            case PostAdapter.POST_WITH_TEXT:
            default:
                ft.replace(R.id.flPost, PostWithTextFragment.newInstance(postId));
                break;
        }
        ft.commit();
    }

    private void setUpCommentButton(String postId) {
        LikeButton commentBtn = (LikeButton)binding.postDetailActions.findViewById(R.id.comment_button);
        commentBtn.setOnClickListener(v -> {
            Intent intent = new Intent(PostDetailsActivity.this, HolderActivity.class);
            intent.putExtra("frag_type","comments");
            intent.putExtra("postId", postId);
            startActivity(intent);
        });
    }

}
