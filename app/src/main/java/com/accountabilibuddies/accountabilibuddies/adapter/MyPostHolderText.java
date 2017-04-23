package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
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

    @BindView(R.id.myHoriSeparator)
    View view;

    @BindView(R.id.cvMyPostText)
    CardView cardView;

    private final static int FADE_DURATION = 1000;

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

            setScaleAnimation(cardView);
        }

    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);

    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim =
                new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

}
