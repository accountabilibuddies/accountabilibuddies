package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.activity.ChallengeOneOnOneActivity;
import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.accountabilibuddies.accountabilibuddies.util.DateUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.peekandpop.shalskar.peekandpop.PeekAndPop;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MyPostHolderImage extends OneOnOneViewHolder {

    @BindView(R.id.myPostImage)
    ImageView imageView;

    @BindView(R.id.myPostImageDate)
    TextView textView;

    public MyPostHolderImage(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void viewBasedOnPost(Post post, Context context) {

        imageView.setImageResource(0);
        if (post != null) {
            if (post.getImageUrl() != null) {
                Glide.with(context)
                        .load(post.getImageUrl())
                        .bitmapTransform(new RoundedCornersTransformation(context,5,0))
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imageView);
            }
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
                textView.setText(DateUtils.getTimeFromDate(post.getCreatedAt()));
            }
        }
    }
}
