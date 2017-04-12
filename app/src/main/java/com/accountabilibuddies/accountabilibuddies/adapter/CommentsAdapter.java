package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.model.Comment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsAdapter extends
        RecyclerView.Adapter<CommentsAdapter.MyViewHolder> {

    private ArrayList<Comment> comments;
    private Context context;

    public CommentsAdapter(Context context, ArrayList<Comment> comments) {
        this.comments = comments;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Comment comment = comments.get(position);

        if (comment != null) {
            //Glide.with(context)
            //        .load(//Facebook image)
            //        .into(holder.profile_image);

            holder.comment.setText(comment.getComment());
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivProfileImage)
        ImageView profile_image;

        @BindView(R.id.tvComment)
        TextView comment;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
