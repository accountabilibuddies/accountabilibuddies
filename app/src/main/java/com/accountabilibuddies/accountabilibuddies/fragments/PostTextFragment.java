package com.accountabilibuddies.accountabilibuddies.fragments;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.databinding.FragmentPostTextBinding;
import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.accountabilibuddies.accountabilibuddies.util.Constants;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;

public class PostTextFragment extends DialogFragment {

    private PostTextListener listener;
    private FragmentPostTextBinding binding;

    public interface PostTextListener {
        void onFinishPost(Post post);
    }

    static final String TAG = PostTextFragment.class.getSimpleName();
    static final String CHALLENGE_ID = "challengeId";

    public PostTextFragment() {
    }

    public static PostTextFragment getInstance(String challengeId) {
        PostTextFragment frag = new PostTextFragment();
        Bundle args = new Bundle();
        args.putString(CHALLENGE_ID, challengeId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PostTextListener) {
            listener = (PostTextListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement PostTextFragment.PostTextListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_text,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**
         * Code to set the count and style the compose Post body
         */
        binding.etPost.setHorizontallyScrolling(false);
        binding.etPost.setHintTextColor(Color.GRAY);
        binding.etPost.setTextColor(Color.GRAY);

        /**
         * Listener for closing the compose fragment
         */
        binding.ivClose.setOnClickListener(ivButton -> dismiss());

        /**
         * Listener for Posting
         */
        binding.btnPost.setOnClickListener(btnPostView -> {
            String post = binding.etPost.getText().toString();
            if(!post.isEmpty()){
                postPost(post);
            }
            dismiss();
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    /**
     * Set the keyboard soft input
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /**
     * Sets the windows to the correct size for this fragment
     */
    @Override
    public void onResume() {

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

    /**
     * Function handles the Post post
     * @param postStr
     */
    public void postPost(String postStr) {

        Post post = new Post();
        post.setType(Constants.TYPE_TEXT);
        post.setText(postStr);
        List<ParseUser> users = new ArrayList<>();
        post.setLikeList(users);
        post.setOwner(ParseUser.getCurrentUser());
        APIClient.getClient().createPost(post, getArguments().getString(CHALLENGE_ID),
            new APIClient.PostListener() {
                @Override
                public void onSuccess() {
                    listener.onFinishPost(post);
                }

                @Override
                public void onFailure(String error_message) {
                    //TODO: handle failure
                }
            });
    }
}