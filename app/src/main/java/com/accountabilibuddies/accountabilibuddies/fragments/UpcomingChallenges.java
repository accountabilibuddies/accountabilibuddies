package com.accountabilibuddies.accountabilibuddies.fragments;


import com.accountabilibuddies.accountabilibuddies.application.ParseApplication;

public class UpcomingChallenges extends ChallengesFragment {
    @Override
    protected void openChallenge(int position) {
        //Do not open Challenge yet
    }

    @Override
    protected void getChallenges() {
        mChallengeList.clear();
        mAdapter.setChallengeType(2);
        client.getUpcomingChallengeList(ParseApplication.getCurrentUser(), listener);
    }
}
