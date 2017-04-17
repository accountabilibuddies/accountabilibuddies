package com.accountabilibuddies.accountabilibuddies.adapter;

import android.view.View;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;

import butterknife.BindView;

public class PostWithTextViewHolder extends PostViewHolder  {

    @BindView(R.id.tvPost)
    TextView text;

    public PostWithTextViewHolder(View itemView) {
        super(itemView);
    }

    public TextView getText() {
        return text;
    }

    public void setText(TextView text) {
        this.text = text;
    }
}
