package com.accountabilibuddies.accountabilibuddies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.accountabilibuddies.accountabilibuddies.R;
import com.parse.ParseImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostWithImageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.ivPost)
    ParseImageView imageView;

    public PostWithImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public ParseImageView getImageView() {
        return imageView;
    }

    public void setImageView(ParseImageView imageView) {
        this.imageView = imageView;
    }
}
