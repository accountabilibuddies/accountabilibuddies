package com.accountabilibuddies.accountabilibuddies.adapter;

import android.view.View;
import android.widget.ImageView;

import com.accountabilibuddies.accountabilibuddies.R;

import butterknife.BindView;

public class PostWithImageViewHolder extends PostViewHolder {

    @BindView(R.id.ivPost)
    ImageView imageView;

    public PostWithImageViewHolder(View itemView) {
        super(itemView);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
