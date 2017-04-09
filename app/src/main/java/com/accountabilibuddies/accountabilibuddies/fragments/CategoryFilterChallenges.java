package com.accountabilibuddies.accountabilibuddies.fragments;

import com.accountabilibuddies.accountabilibuddies.util.Constants;

import java.util.ArrayList;

public class CategoryFilterChallenges extends ChallengesFragment {
    @Override
    protected void getChallenges() {

        //Mock category, this will replace the choice user makes in settings or after first login
        ArrayList<Integer> categories = new ArrayList<>();
        categories.add(Constants.CATEGORY_PHOTOGRAPHY);
        categories.add(Constants.CATEGORY_FITNESS);
        client.getChallengesByCategory(categories, listener);
    }
}
