package com.accountabilibuddies.accountabilibuddies.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.adapter.CommentsAdapter;
import com.accountabilibuddies.accountabilibuddies.adapter.PostAdapter;
import com.accountabilibuddies.accountabilibuddies.application.ParseApplication;
import com.accountabilibuddies.accountabilibuddies.databinding.FragmentPostDetailsBinding;
import com.accountabilibuddies.accountabilibuddies.model.Comment;
import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.accountabilibuddies.accountabilibuddies.util.ImageUtils;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class PostDetailsFragment extends Fragment {

    private Context context;
    private FragmentPostDetailsBinding binding;

    private ArrayList<Comment> comments;

    private Post post;
    private boolean isLiked;

    CommentsAdapter adapter;

    public static PostDetailsFragment newInstance(String postId, String challengeDescription, int viewType) {

        PostDetailsFragment postDetailsFragment = new PostDetailsFragment();

        Bundle args = new Bundle();
        args.putString("postId", postId);
        args.putString("challengeDescription", challengeDescription);
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
        String challengeDescription = getArguments().getString("challengeDescription");
        int viewType = getArguments().getInt("viewType");

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_details, parent, false);
        //binding.setPostDetailsViewModel(new PostDetailsViewModel(getActivity(), postId));

        showPost(postId, viewType);
        addChallengeDescription(challengeDescription);
        showComments(postId);
        setUpNewCommentListener();
        showLikeStatus(postId);
        //setUpOnLikeListener(postId);

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
        EditText etComment = (EditText) binding.lNewComment.findViewById(R.id.etComment);

        ibComment.setOnClickListener(
                (View v) -> {

                    String comment = etComment.getText().toString();
                    etComment.setText("");
                    postComment(comment);

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
        );

        addCurrentUserAvatar();
    }

    private void addCurrentUserAvatar() {

        ImageView ivAvatar = (ImageView) binding.lNewComment.findViewById(R.id.ivAvatar);

        ParseUser user = ParseUser.getCurrentUser();
        String profilePhotoUrl = (String) user.get("profilePhotoUrl");

        ImageUtils.loadCircularProfileImage(
            context,
            profilePhotoUrl,
            ivAvatar
        );
    }

    private void addChallengeDescription(String description) {

        TextView tvDescription = (TextView) binding.lDetails.findViewById(R.id.tvDescription);
        tvDescription.setText(description);
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

//    private void setUpOnLikeListener(String postId) {
//
//        binding.fabLike.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//
//                    setLikeStatus(!isLiked);
//                    onLikePost(postId);
//                }
//
//                return true;
//            }
//        });
//    }

//    private void onLikePost(String postId) {
//
//        APIClient.getClient().likeUnlikePost(postId, isLiked, new APIClient.PostListener() {
//
//            @Override
//            public void onSuccess() {
//                setLikeStatus(isLiked);
//            }
//
//            @Override
//            public void onFailure(String errorMessage) {
//
//            }
//        });
//    }

    private void showLikeStatus(String postId) {

        APIClient.getClient().getLikes(postId, new APIClient.GetLikesListener() {

            @Override
            public void onSuccess(List<ParseUser> usersWhoHaveLiked) {

//                if (usersWhoHaveLiked.contains(ParseUser.getCurrentUser())) {
//                    setLikeStatus(true);
//                } else {
//                    setLikeStatus(false);
//                }
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    private void setLikeStatus(boolean isLiked) {
//
//        this.isLiked = isLiked;
//
//        if (isLiked) {
//            binding.fabLike.setImageDrawable(getActivity().getDrawable(R.drawable.full_heart));
//        } else {
//            binding.fabLike.setImageDrawable(getActivity().getDrawable(R.drawable.empty_heart));
//        }
//    }
}
