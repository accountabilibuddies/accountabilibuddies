package com.accountabilibuddies.accountabilibuddies.fragments;


import com.parse.ParseUser;

public class CurrentChallenges extends ChallengesFragment {
    @Override
    protected void getChallenges() {
        client.getChallengeList(ParseUser.getCurrentUser(), listener);
    }
}
