package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.model.Challenge;
import com.accountabilibuddies.accountabilibuddies.util.AnimUtils;
import com.accountabilibuddies.accountabilibuddies.util.DateUtils;
import com.accountabilibuddies.accountabilibuddies.util.ImageUtils;
import com.parse.ParseException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.accountabilibuddies.accountabilibuddies.util.Constants.TYPE_MULTI_USER;

public class ChallengeAdapter extends
        RecyclerView.Adapter<ChallengeAdapter.MyViewHolder> {

    private static final int ANIMATED_ITEMS_COUNT = 3;
    private int challengeType;
    private ArrayList<Challenge> challengeList;
    private int lastAnimatedPosition = -1;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.challengeName)
        TextView challengeName;

        @BindView(R.id.challengeDescription)
        TextView challengeDescription;

        @BindView(R.id.ivChallengeImage)
        ImageView challengeImage;

        @BindView(R.id.ivProfileImage)
        ImageView profileImage;

        @BindView(R.id.ivMember1)
        ImageView ivMember1;

        @BindView(R.id.ivMember2)
        ImageView ivMember2;

        @BindView(R.id.tvMemberCount)
        TextView count;

        @BindView(R.id.tvDateText)
        TextView dateText;

        @BindView(R.id.tvDate)
        TextView date;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ChallengeAdapter(Context context, ArrayList<Challenge> challengeList) {
        this.challengeList = challengeList;
        this.context = context;
    }

    @Override
    public ChallengeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_challenge, parent, false);

        return new MyViewHolder(itemView);
    }

    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(AnimUtils.getScreenHeight(context));
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }

    @Override
    public void onBindViewHolder(ChallengeAdapter.MyViewHolder holder, int position) {
        Challenge challenge = challengeList.get(position);

        runEnterAnimation(holder.itemView, position);

        if (challenge != null) {
            holder.challengeImage.setImageResource(0);
            holder.profileImage.setImageResource(0);

            ImageUtils.loadProfileImage(
                context,
                challenge.getOwnerProfileImageUrl(),
                holder.profileImage
            );

            holder.challengeName.setText(challenge.getName());
            holder.challengeDescription.setText(challenge.getDescription());

            if (challenge.getImageUrl() != null) {

                ImageUtils.loadImageWithRoundedCorners(
                    context,
                    challenge.getImageUrl(),
                    holder.challengeImage
                );
            }

            try {
                ImageUtils.loadProfileImage(
                        context,
                        challenge.getUserList().get(0).fetchIfNeeded().getString("profilePhotoUrl"),
                        holder.ivMember1
                );
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                ImageUtils.loadProfileImage(
                        context,
                        challenge.getUserList().get(1).fetchIfNeeded().getString("profilePhotoUrl"),
                        holder.ivMember2
                );
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (challenge.getType() == TYPE_MULTI_USER ) {
                holder.count.setBackground(context.getResources().getDrawable(R.drawable.round_textview));
                holder.count.setText("+" + String.valueOf(challenge.getUserList().size() - 2));
            } else {
                holder.count.setBackground(context.getResources().getDrawable(R.drawable.trans_textview));
            }

            switch (challengeType) {
                case 1://Current challenges
                    holder.dateText.setText("Ends:");
                    holder.date.setText(DateUtils.getRelativeTimeFuture(challenge.getEndDate()));
                    break;
                case 2://Upcoming challenges
                    holder.dateText.setText("Starts:");
                    holder.date.setText(DateUtils.getDateFromDate(challenge.getStartDate()));
                    break;
                case 3://Completed challenges
                    holder.dateText.setText("Ended:");
                    holder.date.setText(DateUtils.getRelativeTimeAgo(challenge.getEndDate()));
                    break;
            }
        }
    }

    public void setChallengeType(int type) {
        this.challengeType = type;
    }

    @Override
    public int getItemCount() {
        return challengeList.size();
    }
}
