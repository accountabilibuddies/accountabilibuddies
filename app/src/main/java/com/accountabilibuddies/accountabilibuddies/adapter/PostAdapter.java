package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.activity.ChallengeDetailsActivity;
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

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Post> postList;
    private Context context;
    private GoogleMap map;
    private ImageButton likeBtn, commentBtn;
    private final int POST_WITH_IMAGE = 0, POST_WITH_VIDEO = 1,
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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Post post = postList.get(position);

        APIClient client = APIClient.getClient();

        if (post != null) {
            switch (holder.getItemViewType()) {
                case POST_WITH_IMAGE:
                    PostWithImageViewHolder imgVH = (PostWithImageViewHolder) holder;
                    if (post.getImageUrl() != null)
                        Glide.with(context)
                                .load(post.getImageUrl())
                                .into(imgVH.getImageView());
                    likeBtn = imgVH.getPostLike();
                    commentBtn = imgVH.getPostComment();
                    break;

                case POST_WITH_VIDEO:
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
                        }
                    });
                    likeBtn = locVH.getPostLike();
                    commentBtn = locVH.getPostComment();
                    break;

                case POST_WITH_TEXT:
                default:
                    PostWithTextViewHolder textVH = (PostWithTextViewHolder) holder;
                    textVH.getText().setText(post.getText());
                    likeBtn = textVH.getPostLike();
                    commentBtn = textVH.getPostComment();
                    break;
            }

            if (post.isLiked())
                likeBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.red_heart));

            likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    post.setLiked();
                    client.likeUnlikePost(post.getObjectId(), post.isLiked(), new APIClient.postListener() {
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
            });
            commentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = ((ChallengeDetailsActivity)context).getSupportFragmentManager();
                    CommentsFragment fragment = CommentsFragment.getInstance(post.getObjectId());
                    fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
                    fragment.show(fm, "");
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
