package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.accountabilibuddies.accountabilibuddies.util.DateUtils;
import com.bumptech.glide.Glide;

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
                        .into(imageView);
            }

            if(post.getCreatedAt()!=null) {
                textView.setText(DateUtils.getTimeFromDate(post.getCreatedAt()));
            }
        }
    }
}
