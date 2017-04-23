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
import com.accountabilibuddies.accountabilibuddies.databinding.DetailImageBinding;
import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.accountabilibuddies.accountabilibuddies.util.ImageUtils;

public class PostWithImageFragment extends Fragment {

    DetailImageBinding binding;

    public static PostWithImageFragment newInstance(String postId) {

        PostWithImageFragment fragment = new PostWithImageFragment();

        Bundle args = new Bundle();
        args.putString("postId", postId);

        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.detail_image, parent, false);

        String postId = getArguments().getString("postId");

        APIClient.getClient().getPostById(postId, new APIClient.GetPostListener() {

            @Override
            public void onSuccess(Post post) {
                setUpImage(post);
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(getContext(), "Failed to get post " + postId, Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    public void setUpImage(Post post) {

        if (post.getImageUrl() != null) {

            ImageUtils.loadPostImage(
                getContext(),
                post.getImageUrl(),
                binding.ivChallengeImage
            );
        }
    }
}
