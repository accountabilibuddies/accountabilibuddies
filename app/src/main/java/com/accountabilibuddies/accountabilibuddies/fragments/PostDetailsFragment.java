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
import com.accountabilibuddies.accountabilibuddies.databinding.FragmentPostDetailsBinding;
import com.accountabilibuddies.accountabilibuddies.viewmodel.PostDetailsViewModel;

public class PostDetailsFragment extends Fragment {

    private Context context;
    private FragmentPostDetailsBinding binding;

    public static PostDetailsFragment newInstance() {

        PostDetailsFragment postDetailsFragment = new PostDetailsFragment();

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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_details, parent, false);
        binding.setPostDetailsViewModel(new PostDetailsViewModel());

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
