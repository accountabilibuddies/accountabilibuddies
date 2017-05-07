package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.model.Comment;
import com.accountabilibuddies.accountabilibuddies.util.DateUtils;
import com.accountabilibuddies.accountabilibuddies.util.ImageUtils;
import com.accountabilibuddies.accountabilibuddies.util.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentsAdapter extends
        RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private ArrayList<Comment> comments;
    private Context context;

    public CommentsAdapter(Context context, ArrayList<Comment> comments) {
        this.comments = comments;
        this.context = context;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);

        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {

        Comment comment = comments.get(position);
        String profileImageUrl = comment.getProfileImageUrl();

        if (comment != null) {


            final ForegroundColorSpan darkSpan = new ForegroundColorSpan(
                    ContextCompat.getColor(context, R.color.text_color));
            final StyleSpan boldSpan = new StyleSpan(android.graphics.Typeface.BOLD);

            int length = comment.getOwnerName().length();

            SpannableStringBuilder ssb = new SpannableStringBuilder(
                    StringUtils.generateCommentOwner(comment.getOwnerName()) +
                    comment.getComment());

            ssb.setSpan(boldSpan, 0, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            ssb.setSpan(darkSpan, 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.commentOwner.setText(ssb);

            if (comment.getCreatedAt() != null)
                holder.commentTime.setText(DateUtils.getRelativeTimeAgo(comment.getCreatedAt()));
            else
                holder.commentTime.setText("Just now");

            ImageUtils.loadCircularProfileImage(
                context,
                profileImageUrl,
                holder.profile_image
            );
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivCommentProfileImage)
        ImageView profile_image;

        @BindView(R.id.tvCommentTime)
        TextView commentTime;

        @BindView(R.id.tvCommentOwner)
        TextView commentOwner;


        public CommentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
