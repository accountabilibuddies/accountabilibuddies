package com.accountabilibuddies.accountabilibuddies.fragments;


import com.accountabilibuddies.accountabilibuddies.application.ParseApplication;

public class CurrentChallenges extends ChallengesFragment {
    @Override
    protected void getChallenges() {
        mChallengeList.clear();
        client.getCurrentChallengeList(ParseApplication.getCurrentUser(), listener);
    }
}
