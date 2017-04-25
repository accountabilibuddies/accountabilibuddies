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

public class FriendPostHolderText extends OneOnOneViewHolder {

    @BindView(R.id.friendPostText)
    TextView textView;

    @BindView(R.id.friendPostTextDate)
    TextView dateView;

    @BindView(R.id.cvFriendPostText)
    CardView cardView;

    @BindView(R.id.friendHoriSeparator)
    View hsView;

    public FriendPostHolderText(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void viewBasedOnPost(Post post, Context context, boolean animate) {

        if (post != null) {
            textView.setText(post.getText());

            if(post.getCreatedAt()!=null) {
                dateView.setText(DateUtils.getTimeFromDate(post.getCreatedAt()));
            }

            if(animate) {
                setFriendScaleAnimationX(hsView, cardView);
            }
        }
    }


}
