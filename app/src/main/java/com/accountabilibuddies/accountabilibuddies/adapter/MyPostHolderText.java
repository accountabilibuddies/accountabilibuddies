package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.accountabilibuddies.accountabilibuddies.util.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyPostHolderText extends OneOnOneViewHolder {

    @BindView(R.id.myPostText)
    TextView textView;

    @BindView(R.id.myPostTextDate)
    TextView dateView;

    @BindView(R.id.myHSPostText)
    View hsView;

    @BindView(R.id.cvMyPostText)
    CardView cardView;

    public MyPostHolderText(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void viewBasedOnPost(Post post, Context context) {

        if (post != null) {
            textView.setText(post.getText());

            if (post.getCreatedAt() != null) {
                dateView.setText(DateUtils.getTimeFromDate(post.getCreatedAt()));
            }

            setMyScaleAnimationX(hsView, cardView);
        }

    }
}
