package com.accountabilibuddies.accountabilibuddies.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class OneOnOneViewHolder extends RecyclerView.ViewHolder {

    View itemView;

    private final static int FADE_DURATION = 1000;

    private final static int LINE_FADE_DURATION = 400;

    public OneOnOneViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
    }

    public View getItemView() {
        return itemView;
    }

    public void setMyScaleAnimationX(View hsView, View cardView) {

        AnimatorSet animatorSet = new AnimatorSet();

        cardView.setPivotX(0.0f);
        hsView.setPivotX(0.0f);

        ObjectAnimator hsLineScaleAnimX = ObjectAnimator.ofFloat(hsView, "scaleX", 0.0f, 1.0f)
                .setDuration(LINE_FADE_DURATION);
        ObjectAnimator cardScaleAnimX = ObjectAnimator.ofFloat(cardView, "scaleX", 0.0f, 1.0f)
                .setDuration(FADE_DURATION);

        animatorSet.play(hsLineScaleAnimX).before(cardScaleAnimX);

        animatorSet.start();
    }

    public void setFriendScaleAnimationX(View hsView, View cardView) {

        AnimatorSet animatorSet = new AnimatorSet();

        hsView.setPivotX(hsView.getWidth());
        cardView.setPivotX(cardView.getWidth());

        ObjectAnimator hsLineScaleAnimX = ObjectAnimator.ofFloat(hsView, "scaleX", 0.0f, 1.0f)
                .setDuration(LINE_FADE_DURATION);
        ObjectAnimator cardScaleAnimX = ObjectAnimator.ofFloat(cardView, "scaleX", 0.0f, 1.0f)
                .setDuration(FADE_DURATION);

        animatorSet.play(hsLineScaleAnimX).before(cardScaleAnimX);
        animatorSet.start();

    }
}
