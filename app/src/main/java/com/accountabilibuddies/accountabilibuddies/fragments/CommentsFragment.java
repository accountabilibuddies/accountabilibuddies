package com.accountabilibuddies.accountabilibuddies.fragments;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.adapter.CommentsAdapter;
import com.accountabilibuddies.accountabilibuddies.application.ParseApplication;
import com.accountabilibuddies.accountabilibuddies.databinding.FragmentCommentBinding;
import com.accountabilibuddies.accountabilibuddies.model.Comment;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class CommentsFragment extends DialogFragment {
    private FragmentCommentBinding binding;
    private CommentsAdapter mAdapter;
    private APIClient client;
    private ArrayList<Comment> mCommentList;

    static final String TAG = CommentsFragment.class.getSimpleName();
    static final String POST_ID = "postId";

    public CommentsFragment() {
    }

    public static CommentsFragment getInstance(String postId) {
        CommentsFragment frag = new CommentsFragment();
        Bundle args = new Bundle();
        args.putString(POST_ID, postId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment,container,false);

        client = APIClient.getClient();

        mCommentList = new ArrayList<>();
        mAdapter = new CommentsAdapter(getContext(), mCommentList);
        binding.rVComments.setAdapter(mAdapter);
        binding.rVComments.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        binding.rVComments.addItemDecoration(itemDecoration);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rVComments.setLayoutManager(layoutManager);

        binding.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postComment(String.valueOf(binding.etComment.getText()));
            }
        });

        //Fetch Comments
        getComments();
        return binding.getRoot();
    }

    private void getComments() {
        client.getCommentList(getArguments().getString(POST_ID), new APIClient.GetCommentsListListener() {

            @Override
            public void onSuccess(List<Comment> commentsList) {
                if (commentsList != null) {
                    mCommentList.clear();
                    mCommentList.addAll(commentsList);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String error_message) {

            }
        });
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
        comment.setUser(ParseApplication.getCurrentUser());
        APIClient.getClient().addComment(getArguments().getString(POST_ID), comment,
            new APIClient.PostListener() {
                @Override
                public void onSuccess() {
                    dismiss();
                }

                @Override
                public void onFailure(String error_message) {
                    //TODO: handle failure
                }
            });
    }
}
