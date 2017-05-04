package com.accountabilibuddies.accountabilibuddies.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.adapter.CommentsAdapter;
import com.accountabilibuddies.accountabilibuddies.adapter.PostAdapter;
import com.accountabilibuddies.accountabilibuddies.databinding.ActivityPostDetailsBinding;
import com.accountabilibuddies.accountabilibuddies.fragments.PostWithImageFragment;
import com.accountabilibuddies.accountabilibuddies.fragments.PostWithLocationFragment;
import com.accountabilibuddies.accountabilibuddies.fragments.PostWithTextFragment;
import com.accountabilibuddies.accountabilibuddies.fragments.PostWithVideoFragment;
import com.accountabilibuddies.accountabilibuddies.model.Comment;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.accountabilibuddies.accountabilibuddies.util.ImageUtils;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PostDetailsActivity extends AppCompatActivity {

    ActivityPostDetailsBinding binding;
    private ArrayList<Comment> comments;
    CommentsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_details);

        String postId = getIntent().getStringExtra("postId");
        String createdAt = getIntent().getStringExtra("createdAt");

        String ownerName = getIntent().getStringExtra("user");
        String profileImage = getIntent().getStringExtra("profileImage");
        Boolean like = getIntent().getBooleanExtra("like", false);
        int viewType = getIntent().getIntExtra("viewType",-1);

        setUpToolbar();
        showUserDetails(ownerName, profileImage);
        showComments(postId);
        setUpNewCommentListener();
        setUpOnLikeButton(postId, like);
        binding.tvPostTime.setText(createdAt);
        binding.tvPostOwner.setText(ownerName);
        ImageUtils.loadCircularProfileImage(
                this,
                profileImage,
                binding.ivProfileImage
        );

        showPost(postId, viewType);

    }

    private void setUpOnLikeButton(String postId, Boolean like) {
        if (like)
            binding.heartButton.setLiked(true);

        binding.heartButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                APIClient.getClient().setLike(postId, true);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                APIClient.getClient().setLike(postId, false);
            }
        });
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

        //binding.pdName.setText(ownerName);

        //ImageUtils.loadCircularProfileImage(this, profileImage, binding.ivPDAvatar);
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

    private void showComments(String postId) {

        comments = new ArrayList<>();
        adapter = new CommentsAdapter(this, comments);

        binding.rvComments.setAdapter(adapter);
        binding.rvComments.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        binding.rvComments.addItemDecoration(itemDecoration);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvComments.setLayoutManager(layoutManager);

        getComments(postId);
    }

    private void getComments(String postId) {

        APIClient.getClient().getCommentList(postId, new APIClient.GetCommentsListListener() {

            @Override
            public void onSuccess(List<Comment> commentsList) {
                if (commentsList != null && !commentsList.isEmpty()) {
                    comments.clear();
                    Collections.reverse(commentsList);
                    comments.addAll(commentsList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String error_message) {

            }
        });
    }

    private void setUpNewCommentListener() {

        ImageButton ibComment = (ImageButton) binding.lNewComment.findViewById(R.id.ibComment);
        EditText tietComment = (EditText) binding.lNewComment.findViewById(R.id.etComment);

        ibComment.setOnClickListener(
                (View v) -> {

                    String comment = tietComment.getText().toString();
                    tietComment.setText("");
                    postComment(comment);

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
        );

        addCurrentUserAvatar();
    }

    private void addCurrentUserAvatar() {

        ImageView ivAvatar = (ImageView) binding.lNewComment.findViewById(R.id.ivAvatar);

        ParseUser user = ParseUser.getCurrentUser();
        String profilePhotoUrl = (String) user.get("profilePhotoUrl");

        ImageUtils.loadCircularProfileImage(
                this,
                profilePhotoUrl,
                ivAvatar
        );
    }

    private void postComment(String commentText) {

        Comment comment = new Comment();

        comment.setText(commentText);
        comment.setUser(ParseUser.getCurrentUser());

        comments.add(0, comment);
        adapter.notifyDataSetChanged();
        APIClient.getClient().addComment(getIntent().getStringExtra("postId"), comment,
            new APIClient.PostListener() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onFailure(String error_message) {
                }
            });
    }

}
