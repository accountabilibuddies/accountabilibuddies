package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.activity.HolderActivity;
import com.accountabilibuddies.accountabilibuddies.activity.PostDetailsActivity;
import com.accountabilibuddies.accountabilibuddies.model.Like;
import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.accountabilibuddies.accountabilibuddies.util.AnimUtils;
import com.accountabilibuddies.accountabilibuddies.util.Constants;
import com.accountabilibuddies.accountabilibuddies.util.DateUtils;
import com.accountabilibuddies.accountabilibuddies.util.ImageUtils;
import com.accountabilibuddies.accountabilibuddies.util.VideoPlayer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.parse.ParseUser;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Post> postList;
    private Context context;
    private GoogleMap map;
    private LikeButton heartBtn, commentBtn;
    private String challengeId;
    private static final int ANIMATED_ITEMS_COUNT = 3;
    private int lastAnimatedPosition = -1;

    public static final int POST_WITH_IMAGE = 0, POST_WITH_VIDEO = 1,
                    POST_WITH_TEXT = 2, POST_WITH_LOCATION = 3;

    public PostAdapter(Context context, String challengeId, List<Post> postList) {
        this.postList = postList;
        this.challengeId = challengeId;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        switch (postList.get(position).getType()) {
            case Constants.TYPE_VIDEO:
                return POST_WITH_VIDEO;
            case Constants.TYPE_IMAGE:
                return POST_WITH_IMAGE;
            case Constants.TYPE_LOCATION:
                return POST_WITH_LOCATION;
            case Constants.TYPE_TEXT:
                default:
                return POST_WITH_TEXT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {
            case POST_WITH_IMAGE:
                View viewImagePost = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_post_image, parent, false);
                viewHolder = new PostWithImageViewHolder(viewImagePost);
                break;

            case POST_WITH_VIDEO:
                View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_video, parent, false);
                viewHolder = new PostWithVideoViewHolder(v2);
                break;

            case POST_WITH_LOCATION:
                View v3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_location, parent, false);
                viewHolder = new PostWithLocationViewHolder(v3);
                break;

            case POST_WITH_TEXT:
                default:
                View viewTextPost = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_post_text, parent, false);
                viewHolder = new PostWithTextViewHolder(viewTextPost);
                break;
        }
        return viewHolder;
    }

    public void setUpPostDetailsHandler(View itemView, final Post post, final int viewType) {

        itemView.setOnClickListener(

            (View v) -> {
                Intent intent = new Intent(context, PostDetailsActivity.class);
                intent.putExtra("postId", post.getObjectId());
                intent.putExtra("user", post.getOwnerName());
                intent.putExtra("profileImage",post.getOwnerProfileImageUrl());
                intent.putExtra("createdAt", DateUtils.getRelativeTimeAgo(post.getCreatedAt()));
                intent.putExtra("viewType", viewType);
                intent.putExtra("like", isLiked(post.getLikeList()));
                //ActivityOptionsCompat options = ActivityOptionsCompat.
                //        makeSceneTransitionAnimation(this, (View)ivPost, "post");
                //context.startActivity(intent, options.toBundle());
                context.startActivity(intent);
            }
        );
    }

    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(AnimUtils.getScreenHeight(context));
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        PostViewHolder holder = (PostViewHolder) viewHolder;

        runEnterAnimation(holder.itemView, position);

        Post post = postList.get(position);
        if (post != null) {
            switch (holder.getItemViewType()) {
                case POST_WITH_IMAGE:
                    PostWithImageViewHolder imgVH = (PostWithImageViewHolder) holder;

                    ImageUtils.loadProfileImage(
                            context,
                            post.getOwnerProfileImageUrl(),
                            imgVH.getIvProfileImage()
                    );

                    imgVH.getTvName().setText(post.getOwnerName());
                    imgVH.getRelativeTime().setText(DateUtils.getRelativeTimeAgo(post.getCreatedAt()));

                    if (post.getImageUrl() != null) {

                        ImageUtils.loadPostImage(
                            context,
                            post.getImageUrl(),
                            imgVH.getImageView()
                        );
                    }

                    setPostButtonValues(imgVH);
                    setUpPostDetailsHandler(imgVH.getItemView(), post, POST_WITH_IMAGE);
                    break;

                case POST_WITH_VIDEO:
                    PostWithVideoViewHolder vidVH = (PostWithVideoViewHolder) holder;

                    ImageUtils.loadProfileImage(
                        context,
                        post.getOwnerProfileImageUrl(),
                        vidVH.getIvProfileImage()
                    );

                    vidVH.getTvName().setText(post.getOwnerName());
                    vidVH.getRelativeTime().setText(DateUtils.getRelativeTimeAgo(post.getCreatedAt()));
                    VideoPlayer.loadVideo(context, vidVH.getVideoView(), post.getVideoUrl());
                    //setUpPostDetailsHandler(videoVH.getItemView, post, POST_WITH_VIDEO);
                    setPostButtonValues(vidVH);
                    break;

                case POST_WITH_LOCATION:
                    PostWithLocationViewHolder locVH = (PostWithLocationViewHolder) holder;

                    ImageUtils.loadProfileImage(
                        context,
                        post.getOwnerProfileImageUrl(),
                        locVH.getIvProfileImage()
                    );

                    locVH.getTvName().setText(post.getOwnerName());
                    locVH.getRelativeTime().setText(DateUtils.getRelativeTimeAgo(post.getCreatedAt()));
                    locVH.getMapview().onCreate(null);
                    locVH.getMapview().getMapAsync(googleMap -> {
                        MapsInitializer.initialize(context);
                        map = googleMap;

                        //set map location
                        LatLng location = new LatLng(post.getLatitude(), post.getLongitude());
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13f));
                        MarkerOptions marker = new MarkerOptions()
                                .position(location);

                        if (post.getAddress() != null)
                            marker.title(post.getAddress());
                        map.addMarker(marker);
                        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                        setUpPostDetailsHandler(locVH.getItemView(), post, POST_WITH_LOCATION);
                    });
                    setPostButtonValues(locVH);
                    break;

                case POST_WITH_TEXT:
                default:
                    PostWithTextViewHolder textVH = (PostWithTextViewHolder) holder;

                    ImageUtils.loadProfileImage(
                        context,
                        post.getOwnerProfileImageUrl(),
                        textVH.getIvProfileImage()
                    );

                    textVH.getTvName().setText(post.getOwnerName());
                    textVH.getRelativeTime().setText(DateUtils.getRelativeTimeAgo(post.getCreatedAt()));
                    textVH.getText().setText(post.getText());
                    setPostButtonValues(textVH);
                    setUpPostDetailsHandler(textVH.getItemView(), post, POST_WITH_TEXT);
                    break;
            }

            if (isLiked(post.getLikeList())) {//If current user is in likeList
                heartBtn.setLiked(true);
            } else {
                heartBtn.setLiked(false);
            }

            setUpLikeButton(post);
            setUpCommentButton(post);
        }
    }

    private boolean isLiked(List<Like> likes) {
        Like isLiked = CollectionUtils.find(
                likes,
                (Like like) -> like.getUser().getObjectId().equals(ParseUser.getCurrentUser().getObjectId())
        );

        return isLiked != null;
    }

    private void setPostButtonValues(PostViewHolder holder) {
        heartBtn = holder.getHeartButton();
        commentBtn = holder.getCommentButton();
    }

    private void setUpLikeButton(Post post) {

        heartBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                APIClient.getClient().setLike(post.getObjectId(), true);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                APIClient.getClient().setLike(post.getObjectId(), false);
            }
        });
    }

    private void setUpCommentButton(Post post) {
        commentBtn.setOnClickListener(v -> {
            //TODO Fix styling
            Intent intent = new Intent(v.getContext(), HolderActivity.class);
            intent.putExtra("frag_type","comments");
            intent.putExtra("postId", post.getObjectId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
