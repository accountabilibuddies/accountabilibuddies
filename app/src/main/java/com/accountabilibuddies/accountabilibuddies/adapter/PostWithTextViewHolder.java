package com.accountabilibuddies.accountabilibuddies.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;

import butterknife.BindView;

public class PostWithTextViewHolder extends PostViewHolder  {

    @BindView(R.id.tvPost)
    TextView text;

    @BindView(R.id.ivProfileImage)
    ImageView ivProfileImage;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvTime)
    TextView relativeTime;

    public PostWithTextViewHolder(View itemView) {
        super(itemView);
    }

    public TextView getText() {
        return text;
    }

    public void setText(TextView text) {
        this.text = text;
    }

    public ImageView getIvProfileImage() {
        return ivProfileImage;
    }

    public void setIvProfileImage(ImageView ivProfileImage) {
        this.ivProfileImage = ivProfileImage;
    }

    public TextView getTvName() {
        return tvName;
    }

    public void setTvName(TextView tvName) {
        this.tvName = tvName;
    }

    public TextView getRelativeTime() {
        return relativeTime;
    }

    public void setRelativeTime(TextView relativeTime) {
        this.relativeTime = relativeTime;
    }
}
