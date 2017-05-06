package com.accountabilibuddies.accountabilibuddies.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.WindowManager;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.adapter.MembersAdapter;
import com.accountabilibuddies.accountabilibuddies.databinding.ActivityPastChallengeDetailsBinding;
import com.accountabilibuddies.accountabilibuddies.model.Scoreboard;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PastChallengeDetailsActivity extends AppCompatActivity {

    ActivityPastChallengeDetailsBinding binding;

    private MembersAdapter mAdapter;
    private APIClient client;
    private ArrayList<Scoreboard> mMembersList;
    private String challengeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_past_challenge_details);

        getWindow().getDecorView().setBackground(getResources().getDrawable(R.drawable.background));

        //Setting toolbar
        setSupportActionBar(binding.toolbar);

        // Display icon in the toolbar
        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        client = APIClient.getClient();

        challengeId = getIntent().getStringExtra("challengeId");

        binding.tvTitleValue.setText(getIntent().getStringExtra("name"));
        binding.tvDescValue.setText(getIntent().getStringExtra("desc"));

        mMembersList = new ArrayList<>();
        mAdapter = new MembersAdapter(this, mMembersList);
        binding.rvPastMembers.setAdapter(mAdapter);
        binding.rvPastMembers.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        binding.rvPastMembers.addItemDecoration(itemDecoration);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvPastMembers.setLayoutManager(layoutManager);

        getMembers();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
        client.getScoreboardList(challengeId, new APIClient.GetScoreboardListListener() {
            @Override
            public void onSuccess(List<Scoreboard> scoreboardList) {
                if (scoreboardList != null) {
                    mMembersList.clear();
                    mMembersList.addAll(scoreboardList);
                    mAdapter.notifyDataSetChanged();

                    try {
                        String currentUser = ParseUser.getCurrentUser().fetchIfNeeded().getObjectId();

                        for(Scoreboard obj: scoreboardList ) {
                            if(currentUser.equals(obj.getUser().getObjectId())) {
                                binding.tvMoneyBet.append(String.valueOf(obj.getMoney()) + "$");
                                binding.tvMoneyEarned.append(String.valueOf(obj.getMoney()) + "$");
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(String error_message) {}
        });
    }
}
