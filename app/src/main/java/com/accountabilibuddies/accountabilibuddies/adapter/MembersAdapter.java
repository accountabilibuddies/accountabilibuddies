package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.model.Scoreboard;
import com.accountabilibuddies.accountabilibuddies.util.ImageUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MembersAdapter extends
        RecyclerView.Adapter<MembersAdapter.MyViewHolder>  {

    private ArrayList<Scoreboard> members;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivProfileImage)
        ImageView profile_image;

        @BindView(R.id.tvMemberName)
        TextView name;

        @BindView(R.id.tvMemberPoints)
        TextView points;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public MembersAdapter(Context context, ArrayList<Scoreboard> members) {
        this.members = members;
        this.context = context;
    }

    @Override
    public MembersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_member, parent, false);

        return new MembersAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MembersAdapter.MyViewHolder holder, int position) {
        Scoreboard user = members.get(position);

        if (user != null) {

            ImageUtils.loadProfileImage(
                context,
                user.getUser().getString("profilePhotoUrl"),
                holder.profile_image
            );

            holder.name.setText(user.getUser().getString("name"));
            holder.points.setText(user.getPoints() + " pts");
        }
    }

    @Override
    public int getItemCount() {
        return members.size();
    }
}
