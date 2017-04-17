package com.accountabilibuddies.accountabilibuddies.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class OneOnOneViewHolder extends RecyclerView.ViewHolder {

    View itemView;

    public OneOnOneViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
    }

    public View getItemView() {
        return itemView;
    }
}
