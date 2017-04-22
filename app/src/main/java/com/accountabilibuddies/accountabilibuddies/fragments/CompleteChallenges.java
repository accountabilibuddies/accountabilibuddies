package com.accountabilibuddies.accountabilibuddies.fragments;

import com.accountabilibuddies.accountabilibuddies.application.ParseApplication;

public class CompleteChallenges extends ChallengesFragment {
    @Override
    protected void getChallenges() {
        mChallengeList.clear();
        client.getCompleteChallengeList(ParseApplication.getCurrentUser(), listener);
    }
}
