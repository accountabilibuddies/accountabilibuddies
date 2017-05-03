package com.accountabilibuddies.accountabilibuddies.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.google.android.gms.maps.MapView;

import butterknife.BindView;

public class PostWithLocationViewHolder extends PostViewHolder {

    @BindView(R.id.mapview)
    MapView mapview;

    @BindView(R.id.ivProfileImage)
    ImageView ivProfileImage;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvTime)
    TextView relativeTime;

    public PostWithLocationViewHolder(View itemView) {
        super(itemView);
    }

    public MapView getMapview() {
        return mapview;
    }

    public void setMapview(MapView mapview) {
        this.mapview = mapview;
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
