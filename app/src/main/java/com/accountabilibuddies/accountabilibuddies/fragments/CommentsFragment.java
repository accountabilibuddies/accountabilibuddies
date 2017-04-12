package com.accountabilibuddies.accountabilibuddies.fragments;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.databinding.FragmentCommentBinding;
import com.accountabilibuddies.accountabilibuddies.model.Comment;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.parse.ParseUser;

public class CommentsFragment extends DialogFragment {
    private FragmentCommentBinding binding;

    static final String TAG = CommentsFragment.class.getSimpleName();
    static final String POST_ID = "postId";

    public CommentsFragment() {
    }

    public static CommentsFragment getInstance(String challengeId) {
        CommentsFragment frag = new CommentsFragment();
        Bundle args = new Bundle();
        args.putString(POST_ID, challengeId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment,container,false);
        return binding.getRoot();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

    public void postComment(String postComment) {
        Comment comment = new Comment();

        comment.setText(postComment);
        comment.setUser(ParseUser.getCurrentUser());
        APIClient.getClient().addComment(getArguments().getString(POST_ID), comment,
            new APIClient.postListener() {
                @Override
                public void onSuccess() {
                    //TODO: Handle success
                }

                @Override
                public void onFailure(String error_message) {
                    //TODO: handle failure
                }
            });
    }
}
