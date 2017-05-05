package com.accountabilibuddies.accountabilibuddies.activity;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.adapter.MembersAdapter;
import com.accountabilibuddies.accountabilibuddies.databinding.ActivityChallengeMembersBinding;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ChallengeMembersActivity extends AppCompatActivity {

    private ActivityChallengeMembersBinding binding;
    private MembersAdapter mAdapter;
    private APIClient client;
    private ArrayList<ParseUser> mMembersList;
    private String challengeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_challenge_members);

        challengeId = getIntent().getStringExtra("challengeId");

        setUpToolbar();
        client = APIClient.getClient();

        mMembersList = new ArrayList<>();
        mAdapter = new MembersAdapter(this, mMembersList);
        binding.rvMembers.setAdapter(mAdapter);
        binding.rvMembers.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        binding.rvMembers.addItemDecoration(itemDecoration);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvMembers.setLayoutManager(layoutManager);

        getMembers();
        return;
    }

    private void setUpToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Leaderboard");
        getSupportActionBar().setSubtitle("Who is leading this week ?");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().getDecorView().setBackground(getResources().getDrawable(R.drawable.background));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getMembers() {
        client.getMembersList(challengeId, new APIClient.GetMembersListListener() {
            @Override
            public void onSuccess(List<ParseUser> membersList) {
                if (membersList != null) {
                    mMembersList.clear();
                    mMembersList.addAll(membersList);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String error_message) {

            }
        });
    }
}
