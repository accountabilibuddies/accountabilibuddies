package com.accountabilibuddies.accountabilibuddies.fragments;


import android.support.design.widget.Snackbar;

import com.accountabilibuddies.accountabilibuddies.modal.Challenge;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.parse.ParseUser;

import java.util.List;

public class CurrentChallenges extends ChallengesFragment {
    @Override
    protected void getChallenges() {
        client.getChallengeList(ParseUser.getCurrentUser(), listener);
    }
}
