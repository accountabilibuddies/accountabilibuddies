package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.accountabilibuddies.accountabilibuddies.util.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendPostHolderText extends RecyclerView.ViewHolder {

    @BindView(R.id.friendPostText)
    TextView textView;

    @BindView(R.id.friendPostTextDate)
    TextView dateView;

    public FriendPostHolderText(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void viewBasedOnPost(Post post, Context context) {

        if (post != null) {
            textView.setText(post.getText());

            if(post.getCreatedAt()!=null) {
                dateView.setText(DateUtils.getTimeFromDate(post.getCreatedAt()));
            }

        }
    }
}
