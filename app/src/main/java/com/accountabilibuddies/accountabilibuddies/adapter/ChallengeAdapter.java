package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.model.Challenge;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ChallengeAdapter extends
        RecyclerView.Adapter<ChallengeAdapter.MyViewHolder> {

    private ArrayList<Challenge> challengeList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.challengeName)
        TextView challengeName;

        @BindView(R.id.challengeDescription)
        TextView challengeDescription;

        @BindView(R.id.ivChallengeImage)
        ImageView challengeImage;

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

    @Override
    public void onBindViewHolder(ChallengeAdapter.MyViewHolder holder, int position) {
        Challenge challenge = challengeList.get(position);

        if (challenge != null) {
            holder.challengeName.setText(challenge.getName());
            holder.challengeDescription.setText(challenge.getDescription());

            if (challenge.getImageUrl() != null)
                Glide.with(context)
                        .load(challenge.getImageUrl())
                        .bitmapTransform(new RoundedCornersTransformation(context, 20,0))
                        .into(holder.challengeImage);
        }
    }

    @Override
    public int getItemCount() {
        return challengeList.size();
    }
}
