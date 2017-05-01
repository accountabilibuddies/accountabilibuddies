package com.accountabilibuddies.accountabilibuddies.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.adapter.CommentsAdapter;
import com.accountabilibuddies.accountabilibuddies.databinding.FragmentPostDetailsBinding;
import com.accountabilibuddies.accountabilibuddies.model.Comment;

import java.util.ArrayList;

public class PostDetailsFragment extends Fragment {

    private Context context;
    private FragmentPostDetailsBinding binding;

    private ArrayList<Comment> comments;

    CommentsAdapter adapter;

    public static PostDetailsFragment newInstance(String postId, int viewType) {

        PostDetailsFragment postDetailsFragment = new PostDetailsFragment();

        Bundle args = new Bundle();
        args.putString("postId", postId);
        args.putInt("viewType", viewType);

        postDetailsFragment.setArguments(args);

        return postDetailsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        String postId = getArguments().getString("postId");
        int viewType = getArguments().getInt("viewType");

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_details, parent, false);

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


}
