package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.activity.ChallengeOneOnOneActivity;
import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.accountabilibuddies.accountabilibuddies.util.DateUtils;
import com.bumptech.glide.Glide;
import com.peekandpop.shalskar.peekandpop.PeekAndPop;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class FriendPostHolderImage extends OneOnOneViewHolder {

    @BindView(R.id.friendPostImage)
    ImageView imageView;

    @BindView(R.id.friendPostImageDate)
    TextView dateView;

    @BindView(R.id.friendHoriSeparator)
    View hsView;

    @BindView(R.id.cvFriendPostImage)
    CardView cardView;

    public FriendPostHolderImage(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void viewBasedOnPost(Post post, Context context) {

        if (post != null) {
            imageView.setImageResource(0);
            if (post.getImageUrl() != null)
                Glide.with(context)
                        .load(post.getImageUrl())
                        .bitmapTransform(new RoundedCornersTransformation(context,5,0))
                        .into(imageView);

            imageView.setOnLongClickListener(view -> {

                PeekAndPop peekAndPop = new PeekAndPop.Builder((ChallengeOneOnOneActivity)context)
                        .peekLayout(R.layout.fragment_post_popup)
                        .longClickViews(view)
                        .build();

                View peekView = peekAndPop.getPeekView();
                ImageView iv = (ImageView) peekView.findViewById(R.id.ivPopup);
                Glide.with(context)
                        .load(post.getImageUrl())
                        .bitmapTransform(new RoundedCornersTransformation(context,5,0))
                        .into(iv);

                return false;
            });

            if(post.getCreatedAt()!=null) {
                dateView.setText(DateUtils.getTimeFromDate(post.getCreatedAt()));
            }

            setFriendScaleAnimationX(hsView, cardView);
        }
    }


}
