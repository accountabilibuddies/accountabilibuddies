package com.accountabilibuddies.accountabilibuddies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.accountabilibuddies.accountabilibuddies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostWithImageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image)
    ImageView imageView;

    public PostWithImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
