package com.accountabilibuddies.accountabilibuddies.fragments;


import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.adapter.MembersAdapter;
import com.accountabilibuddies.accountabilibuddies.databinding.FragmentChallengeMembersBinding;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ChallengeMembersFragment extends DialogFragment {

    private FragmentChallengeMembersBinding binding;
    private MembersAdapter mAdapter;
    private APIClient client;
    private ArrayList<ParseUser> mMembersList;

    static final String TAG = CommentsFragment.class.getSimpleName();
    static final String CHALLENGE_ID = "challengeId";

    public ChallengeMembersFragment() {}

    public static ChallengeMembersFragment getInstance(String challengeId) {
        ChallengeMembersFragment frag = new ChallengeMembersFragment();
        Bundle args = new Bundle();
        args.putString(CHALLENGE_ID, challengeId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_challenge_members,container,false);

        client = APIClient.getClient();

        mMembersList = new ArrayList<>();
        mAdapter = new MembersAdapter(getContext(), mMembersList);
        binding.rvMembers.setAdapter(mAdapter);
        binding.rvMembers.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        binding.rvMembers.addItemDecoration(itemDecoration);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvMembers.setLayoutManager(layoutManager);

        getMembers();
        return binding.getRoot();
    }

    private void getMembers() {
        client.getMembersList(getArguments().getString(CHALLENGE_ID), new APIClient.GetMembersListListener() {

            @Override
            public void onSuccess(List<ParseUser> membersList) {
                if (membersList != null) {
                    mMembersList.clear();
                    mMembersList.addAll(membersList);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String error_message) {

            }
        });
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

}
