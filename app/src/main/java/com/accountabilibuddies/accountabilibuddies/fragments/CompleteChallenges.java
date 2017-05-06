package com.accountabilibuddies.accountabilibuddies.fragments;

import android.content.Intent;

import com.accountabilibuddies.accountabilibuddies.activity.PastChallengeDetailsActivity;
import com.accountabilibuddies.accountabilibuddies.application.ParseApplication;
import com.accountabilibuddies.accountabilibuddies.model.Challenge;

public class CompleteChallenges extends ChallengesFragment {
    @Override
    protected void openChallenge(int position) {
        Challenge challenge = mChallengeList.get(position);

        Intent intent = new Intent(getActivity(), PastChallengeDetailsActivity.class);
        intent.putExtra("challengeId", challenge.getObjectId());
        intent.putExtra("name", challenge.getName());
        intent.putExtra("desc", challenge.getDescription());
        getActivity().startActivity(intent);
    }

    @Override
    protected void getChallenges() {
        mAdapter.setChallengeType(3);
        client.getCompleteChallengeList(ParseApplication.getCurrentUser(), listener);
    }
}
