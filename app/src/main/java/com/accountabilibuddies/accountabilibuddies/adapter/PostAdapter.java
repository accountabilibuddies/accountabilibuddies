package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.activity.ChallengeDetailsActivity;
import com.accountabilibuddies.accountabilibuddies.activity.PostDetailsActivity;
import com.accountabilibuddies.accountabilibuddies.fragments.CommentsFragment;
import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.accountabilibuddies.accountabilibuddies.util.Constants;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseException;

import org.parceler.Parcels;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Post> postList;
    private Context context;
    private GoogleMap map;
    private ImageButton likeBtn, commentBtn;
    private CardView cvPost;
    private TextView likesCount;

    APIClient client = APIClient.getClient();
    public static final int POST_WITH_IMAGE = 0, POST_WITH_VIDEO = 1,
                    POST_WITH_TEXT = 2, POST_WITH_LOCATION = 3;

    public PostAdapter(Context context, List<Post> postList) {
        this.postList = postList;
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

    public void setUpPostDetailsHandler(View itemView, final String postId, final int viewType) {

        itemView.setOnTouchListener(

            (View v, MotionEvent event) -> {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    Intent intent = new Intent(context, PostDetailsActivity.class);
                    intent.putExtra("postId", postId);
                    intent.putExtra("viewType", viewType);
                    context.startActivity(intent);

                    Toast.makeText(itemView.getContext(), "Set up card view listener.", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        );
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        PostViewHolder holder = (PostViewHolder) viewHolder;

        Post post = postList.get(position);
        if (post != null) {
            switch (holder.getItemViewType()) {
                case POST_WITH_IMAGE:
                    PostWithImageViewHolder imgVH = (PostWithImageViewHolder) holder;
                    if (post.getImageUrl() != null)
                        Glide.with(context)
                                .load(post.getImageUrl())
                                .into(imgVH.getImageView());
                    setPostButtonValues(imgVH);
                    setUpPostDetailsHandler(imgVH.getItemView(), post.getObjectId(), POST_WITH_IMAGE);
                    break;

                case POST_WITH_VIDEO:
//                    setUpPostDetailsHandler(videoVH.getItemView, post.getObjectId(), POST_WITH_VIDEO);
                    break;

                case POST_WITH_LOCATION:
                    PostWithLocationViewHolder locVH = (PostWithLocationViewHolder) holder;

                    locVH.getMapview().onCreate(null);
                    locVH.getMapview().getMapAsync(new OnMapReadyCallback(){
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            MapsInitializer.initialize(context);
                            map = googleMap;

                            //set map location
                            LatLng location = new LatLng(post.getLatitude(), post.getLongitude());
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13f));
                            map.addMarker(new MarkerOptions().position(location));
                            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                            if (post.getAddress() != null)
                                locVH.getAddress().setText(post.getAddress());
                            else
                                locVH.getAddress().setVisibility(View.GONE);

                            setUpPostDetailsHandler(locVH.getItemView(), post.getObjectId(), POST_WITH_LOCATION);
                        }
                    });
                    setPostButtonValues(locVH);
                    break;

                case POST_WITH_TEXT:
                default:
                    PostWithTextViewHolder textVH = (PostWithTextViewHolder) holder;
                    textVH.getText().setText(post.getText());
                    setPostButtonValues(textVH);
                    setUpPostDetailsHandler(textVH.getItemView(), post.getObjectId(), POST_WITH_TEXT);
                    break;
            }

            likesCount.setText(post.getLikeList().size() + " Likes");

            if (post.isLiked()) {
                likeBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.red_heart));
            }

            setUpLikeButton(post);
            setUpCommentButton(post);
        }
    }

    private void setPostButtonValues(PostViewHolder holder) {

        likeBtn = holder.getPostLike();
        commentBtn = holder.getPostComment();
        likesCount = holder.getLikesCount();
        cvPost = holder.getCardView();
    }

    private void setUpLikeButton(Post post) {

        likeBtn.setOnClickListener(

            (View v) -> {
                post.setLiked();
                client.likeUnlikePost(post.getObjectId(), post.isLiked(), new APIClient.PostListener() {
                    @Override
                    public void onSuccess() {
                        if (post.isLiked())
                            likeBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.red_heart));
                        else
                            likeBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.heart));
                    }

                    @Override
                    public void onFailure(String error_message) { }
                });
            }
        );
    }

    private void setUpCommentButton(Post post) {

        commentBtn.setOnClickListener(
            (View v) -> {
                FragmentManager fm = ((ChallengeDetailsActivity)context).getSupportFragmentManager();
                CommentsFragment fragment = CommentsFragment.getInstance(post.getObjectId());
                fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
                fragment.show(fm, "");
            }
        );
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
