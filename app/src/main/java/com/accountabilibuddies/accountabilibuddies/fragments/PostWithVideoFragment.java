package com.accountabilibuddies.accountabilibuddies.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.databinding.ItemPostVideoBinding;

public class PostWithVideoFragment extends Fragment {

    ItemPostVideoBinding binding;

    public static PostWithVideoFragment newInstance(String postId) {

        PostWithVideoFragment fragment = new PostWithVideoFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.item_post_video, parent, false);

        return binding.getRoot();
    }
}
