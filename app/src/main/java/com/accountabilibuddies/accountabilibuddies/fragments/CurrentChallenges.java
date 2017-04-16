package com.accountabilibuddies.accountabilibuddies.fragments;


import com.accountabilibuddies.accountabilibuddies.application.ParseApplication;
import com.parse.ParseUser;

public class CurrentChallenges extends ChallengesFragment {
    @Override
    protected void getChallenges() {
        client.getChallengeList(ParseApplication.getCurrentUser(), listener);
    }
}
