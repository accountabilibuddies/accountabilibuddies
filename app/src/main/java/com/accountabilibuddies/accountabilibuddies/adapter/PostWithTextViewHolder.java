package com.accountabilibuddies.accountabilibuddies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostWithTextViewHolder extends RecyclerView.ViewHolder  {

    @BindView(R.id.tvPost)
    TextView text;

    @BindView(R.id.ibLike)
    ImageButton postLike;

    @BindView(R.id.ibComment)
    ImageButton postComment;

    public PostWithTextViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getText() {
        return text;
    }

    public void setText(TextView text) {
        this.text = text;
    }

    public ImageButton getPostLike() {
        return postLike;
    }

    public void setPostLike(ImageButton postLike) {
        this.postLike = postLike;
    }

    public ImageButton getPostComment() {
        return postComment;
    }

    public void setPostComment(ImageButton postComment) {
        this.postComment = postComment;
    }
}
