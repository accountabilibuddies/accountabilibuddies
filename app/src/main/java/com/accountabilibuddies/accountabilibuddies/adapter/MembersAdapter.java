package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.bumptech.glide.Glide;
import com.parse.ParseUser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MembersAdapter extends
        RecyclerView.Adapter<MembersAdapter.MyViewHolder>  {

    private ArrayList<ParseUser> members;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivProfileImage)
        ImageView profile_image;

        @BindView(R.id.tvMemberName)
        TextView name;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public MembersAdapter(Context context, ArrayList<ParseUser> members) {
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
        ParseUser user = members.get(position);

        if (user != null) {
            Glide.with(context)
                    .load(user.getString("profilePhotoUrl"))
                    .into(holder.profile_image);

            holder.name.setText(user.getString("name"));
        }

    }

    @Override
    public int getItemCount() {
        return members.size();
    }
}
