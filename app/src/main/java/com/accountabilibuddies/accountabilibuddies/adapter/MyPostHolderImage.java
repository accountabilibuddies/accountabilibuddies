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

public class MyPostHolderImage extends OneOnOneViewHolder {

    @BindView(R.id.myPostImage)
    ImageView imageView;

    @BindView(R.id.myPostImageDate)
    TextView textView;

    @BindView(R.id.cvMyPostImage)
    CardView cardView;

    @BindView(R.id.myHSImage)
    View hsView;

    public MyPostHolderImage(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void viewBasedOnPost(Post post, Context context, boolean animate) {

        imageView.setImageResource(0);
        if (post != null) {
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

                ImageUtils.loadPostImageWithRoundedCorners(
                    context,
                    post.getImageUrl(),
                    iv
                );

                return false;
            });

            if(post.getCreatedAt()!=null) {
                textView.setText(DateUtils.getTimeFromDate(post.getCreatedAt()));
            }

            if(animate) {
                setMyScaleAnimationX(hsView, cardView);
            }
        }
    }
}
