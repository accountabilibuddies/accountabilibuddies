package com.accountabilibuddies.accountabilibuddies.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.adapter.PostAdapter;
import com.accountabilibuddies.accountabilibuddies.databinding.FragmentPostDetailsBinding;
import com.accountabilibuddies.accountabilibuddies.viewmodel.PostDetailsViewModel;

public class PostDetailsFragment extends Fragment {

    private Context context;
    private FragmentPostDetailsBinding binding;

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
        binding.setPostDetailsViewModel(new PostDetailsViewModel());

        showPost(postId, viewType);

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    private void showPost(String postId, int viewType) {

        switch (viewType) {
            case PostAdapter.POST_WITH_IMAGE:
                showPostWithImage(postId);
                break;

            case PostAdapter.POST_WITH_VIDEO:
                showPostWithVideo(postId);
                break;

            case PostAdapter.POST_WITH_LOCATION:
                showPostWithLocation(postId);
                break;

            case PostAdapter.POST_WITH_TEXT:
            default:
                showPostWithText(postId);
                break;
        }
    }

    private void showPostWithImage(String postId) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.replace(R.id.flPost, PostWithImageFragment.newInstance(postId));
        ft.commit();
    }

    private void showPostWithVideo(String postId) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.replace(R.id.flPost, PostWithVideoFragment.newInstance(postId));
        ft.commit();
    }

    private void showPostWithLocation(String postId) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.replace(R.id.flPost, PostWithLocationFragment.newInstance(postId));
        ft.commit();
    }

    private void showPostWithText(String postId) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.replace(R.id.flPost, PostWithTextFragment.newInstance(postId));
        ft.commit();
    }
}
