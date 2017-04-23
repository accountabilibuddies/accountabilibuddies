package com.accountabilibuddies.accountabilibuddies.util;

import android.content.Context;
import android.widget.ImageView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ImageUtils {

    //TODO: need default image for 200x200 as well
    public static final String defaultImage = "https://scontent.xx.fbcdn.net/v/t1.0-1/c15.0.50.50/p50x50/10354686_10150004552801856_220367501106153455_n.jpg?oh=df01630bb28d905025f6ed83bee71510&oe=5987FB2F";

    public static void loadProfileImage(Context context, String url, ImageView ivProfileImage) {

        if (url == null || url.equals(defaultImage)) {

            Glide
                .with(context)
                .load(R.drawable.avatar_placeholder)
                .into(ivProfileImage);

        } else {

            Glide
                .with(context)
                .load(url)
                .placeholder(context.getResources().getDrawable(R.drawable.avatar_placeholder))

                .into(ivProfileImage);
        }
    }

    public static void loadCircularProfileImage(Context context, String url, ImageView ivProfileImage) {

        if (url == null || url.equals(defaultImage)) {

            Glide
                .with(context)
                .load(R.drawable.avatar_placeholder)
                .transform(new AvatarTransform(context))
                .into(ivProfileImage);

        } else {

            Glide
                .with(context)
                .load(url)
                .transform(new AvatarTransform(context))
                .placeholder(context.getResources().getDrawable(R.drawable.avatar_placeholder))
                .into(ivProfileImage);
        }
    }

    public static void loadImageWithRoundedCorners(Context context, String url, ImageView ivImage) {

        Glide
            .with(context)
            .load(url)
            .bitmapTransform(new RoundedCornersTransformation(context, 20, 0))
            .into(ivImage);
    }

    public static void loadPostImage(Context context, String url, ImageView ivImage) {

        loadImage(context, url, ivImage);
    }

    public static void loadImage(Context context, String url, ImageView ivImage) {

        Glide
            .with(context)
            .load(url)
            .into(ivImage);
    }

    public static void loadPostImageWithRoundedCorners(Context context, String url, ImageView ivImage) {

        Glide.with(context)
            .load(url)
            .bitmapTransform(new RoundedCornersTransformation(context, 5, 0))
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .into(ivImage);
    }
}
