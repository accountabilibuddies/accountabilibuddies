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
import com.accountabilibuddies.accountabilibuddies.databinding.ItemPostVideoBinding;
import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;

public class PostWithVideoFragment extends Fragment {

    ItemPostVideoBinding binding;

    public static PostWithVideoFragment newInstance(String postId) {

        PostWithVideoFragment fragment = new PostWithVideoFragment();

        Bundle args = new Bundle();
        args.putString("postId", postId);

        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.item_post_video, parent, false);

        String postId = getArguments().getString("postId");

        APIClient.getClient().getPostById(postId, new APIClient.GetPostListener() {

            @Override
            public void onSuccess(Post post) {
                setUpVideo(post);
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(getContext(), "Failed to get post " + postId, Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    public void setUpVideo(Post post) {

        //TODO: set up video
    }
}
