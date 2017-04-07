package com.accountabilibuddies.accountabilibuddies.fragments;

import android.support.design.widget.Snackbar;

import com.accountabilibuddies.accountabilibuddies.modal.Challenge;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.accountabilibuddies.accountabilibuddies.util.Constants;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class CategoryFilterChallenges extends ChallengesFragment {
    @Override
    protected void getChallenges() {

        //Mock category, this will replace the choice user makes in settings or after first login
        ArrayList<Integer> categories = new ArrayList<Integer>();
        categories.add(Constants.CATEGORY_PHOTOGRAPHY);
        categories.add(Constants.CATEGORY_FITNESS);
        client.getChallengesByCategory(categories, listener);
    }
}
