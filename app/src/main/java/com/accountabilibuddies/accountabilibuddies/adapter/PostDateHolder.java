package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostDateHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.postDate)
    TextView dateView;

    public PostDateHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void viewBasedOnPost(String date, Context context) {
        dateView.setText(date);
    }
}
