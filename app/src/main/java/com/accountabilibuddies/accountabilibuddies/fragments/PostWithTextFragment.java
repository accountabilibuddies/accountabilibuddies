package com.accountabilibuddies.accountabilibuddies.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.databinding.ItemPostTextBinding;
import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;

public class PostWithTextFragment extends Fragment {

    ItemPostTextBinding binding;

    public static PostWithTextFragment newInstance(String postId) {

        PostWithTextFragment fragment = new PostWithTextFragment();

        Bundle args = new Bundle();
        args.putString("postId", postId);

        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.item_post_text, parent, false);

        String postId = getArguments().getString("postId");

        APIClient.getClient().getPostById(postId, new APIClient.GetPostListener() {

            @Override
            public void onSuccess(Post post) {
                setUpText(post);
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(getContext(), "Failed to get post " + postId, Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    public void setUpText(Post post) {

        binding.tvPost.setText(post.getText());
    }
}
