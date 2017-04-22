package com.accountabilibuddies.accountabilibuddies.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.adapter.CommentsAdapter;
import com.accountabilibuddies.accountabilibuddies.adapter.PostAdapter;
import com.accountabilibuddies.accountabilibuddies.application.ParseApplication;
import com.accountabilibuddies.accountabilibuddies.databinding.FragmentPostDetailsBinding;
import com.accountabilibuddies.accountabilibuddies.model.Challenge;
import com.accountabilibuddies.accountabilibuddies.model.Comment;
import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.accountabilibuddies.accountabilibuddies.util.AvatarTransform;
import com.accountabilibuddies.accountabilibuddies.viewmodel.PostDetailsViewModel;
import com.bumptech.glide.Glide;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class PostDetailsFragment extends Fragment {

    private Context context;
    private FragmentPostDetailsBinding binding;

    private ArrayList<Comment> comments;

    private Post post;

    CommentsAdapter adapter;

    public static PostDetailsFragment newInstance(String postId, String challengeId, int viewType) {

        PostDetailsFragment postDetailsFragment = new PostDetailsFragment();

        Bundle args = new Bundle();
        args.putString("postId", postId);
        args.putString("challengeId", challengeId);
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
        String challengeId = getArguments().getString("challengeId");
        int viewType = getArguments().getInt("viewType");

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_details, parent, false);
        binding.setPostDetailsViewModel(new PostDetailsViewModel(getActivity(), postId));

        showPost(postId, viewType);
        addChallengeDetails(challengeId);
        showComments(postId);
        setUpNewCommentListener();
        showNumLikes(postId);

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

    private void setUpNewCommentListener() {

        ImageButton ibComment = (ImageButton) binding.lNewComment.findViewById(R.id.ibComment);
        TextInputEditText tietComment = (TextInputEditText) binding.lNewComment.findViewById(R.id.tietComment);

        ibComment.setOnClickListener(
                (View v) -> {

                    String comment = tietComment.getText().toString();
                    tietComment.setText("");
                    postComment(comment);
                }
        );

        addCurrentUserAvatar();
    }

    private void addCurrentUserAvatar() {

        ImageView ivAvatar = (ImageView) binding.lNewComment.findViewById(R.id.ivAvatar);

        ParseUser user = ParseUser.getCurrentUser();
        String profilePhotoUrl = (String) user.get("profilePhotoUrl");

        Glide.with(context)
                .load(profilePhotoUrl)
                .transform(new AvatarTransform(context))
                .placeholder(context.getDrawable(R.drawable.avatar_placeholder))
                .into(ivAvatar);
    }

    private void addChallengeDetails(String challengeId) {

        APIClient.getClient().getChallengeById(challengeId, new APIClient.GetChallengeListener() {

            @Override
            public void onSuccess(Challenge challenge) {

                TextView tvTitle = (TextView) binding.lDetails.findViewById(R.id.tvTitle);
                TextView tvDescription = (TextView) binding.lDetails.findViewById(R.id.tvDescription);

                tvTitle.setText(challenge.getName());
                tvDescription.setText(challenge.getDescription());
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(getContext(), "Failed to get challenge " + challengeId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postComment(String commentText) {

        Comment comment = new Comment();

        comment.setText(commentText);
        comment.setUser(ParseApplication.getCurrentUser());

        int oldSize = comments.size();

        comments.add(comment);
        adapter.notifyItemRangeChanged(oldSize, 1);

        binding.rvComments.scrollToPosition(oldSize);

        APIClient.getClient().addComment(getArguments().getString("postId"), comment,
                new APIClient.PostListener() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onFailure(String error_message) {
                    }
                });
    }

    private void showComments(String postId) {

        comments = new ArrayList<>();
        adapter = new CommentsAdapter(getContext(), comments);

        binding.rvComments.setAdapter(adapter);
        binding.rvComments.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            binding.rvComments.addItemDecoration(itemDecoration);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            binding.rvComments.setLayoutManager(layoutManager);

        getComments(postId);
    }

    private void getComments(String postId) {

        APIClient.getClient().getCommentList(postId, new APIClient.GetCommentsListListener() {

            @Override
            public void onSuccess(List<Comment> commentsList) {
                if (commentsList != null && !commentsList.isEmpty()) {
                    comments.clear();
                    comments.addAll(commentsList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String error_message) {

            }
        });
    }

    private void showNumLikes(String postId) {

        APIClient.getClient().getLikes(postId, new APIClient.GetLikesListener() {

            @Override
            public void onSuccess(List<ParseUser> usersWhoHaveLiked) {

//                if (!usersWhoHaveLiked.isEmpty()) {
//                    String numLikes = Integer.toString(usersWhoHaveLiked.size());
//
//                    binding.tvNumLikes.setText(numLikes);
//                } else {
//                    binding.tvNumLikes.setVisibility(View.GONE);
//                    binding.ivLikes.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }
}
