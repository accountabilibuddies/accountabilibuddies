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
import com.accountabilibuddies.accountabilibuddies.util.ImageUtils;
import com.peekandpop.shalskar.peekandpop.PeekAndPop;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    public void viewBasedOnPost(Post post, Context context, boolean animate) {

        if (post != null) {
            imageView.setImageResource(0);
            if (post.getImageUrl() != null) {
                ImageUtils.loadPostImageWithRoundedCorners(
                    context,
                    post.getImageUrl(),
                    imageView
                );
            }

            imageView.setOnLongClickListener(view -> {

                PeekAndPop peekAndPop = new PeekAndPop.Builder((ChallengeOneOnOneActivity)context)
                        .peekLayout(R.layout.fragment_post_popup)
                        .longClickViews(view)
                        .build();

                View peekView = peekAndPop.getPeekView();
                ImageView iv = (ImageView) peekView.findViewById(R.id.ivPopup);
                ImageView ivAvatar = (ImageView) peekView.findViewById(R.id.ivAvatarPopup);
                TextView tvName = (TextView) peekView.findViewById(R.id.tvNamePopup);

                ImageUtils.loadProfileImage(
                        context,
                        post.getOwnerProfileImageUrl(),
                        ivAvatar
                );

                tvName.setText(post.getOwnerName());
                ImageUtils.loadPostImageWithRoundedCorners(
                    context,
                    post.getImageUrl(),
                    iv
                );

                return false;
            });

            if(post.getCreatedAt()!=null) {
                dateView.setText(DateUtils.getTimeFromDate(post.getCreatedAt()));
            }

            if(animate) {
                setFriendScaleAnimationX(hsView, cardView);
            }
        }
    }


}
