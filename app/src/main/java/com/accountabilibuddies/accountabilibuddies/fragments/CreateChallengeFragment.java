package com.accountabilibuddies.accountabilibuddies.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.databinding.FragmentCreateChallengeBinding;
import com.accountabilibuddies.accountabilibuddies.modal.Challenge;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.accountabilibuddies.accountabilibuddies.viewmodels.CreateChallengeViewModel;

public class CreateChallengeFragment extends Fragment
        implements CreateChallengeViewModel.CreateChallengeDataListener {

    private FragmentCreateChallengeBinding binding;
    private CreateChallengeViewModel viewModel;

    public CreateChallengeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Setup binding
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_create_challenge, container, false);

        //Create view model for create challenge
        viewModel = new CreateChallengeViewModel(this,getContext());

        //Attached view model to binding(ccvm is the name of the binding)
        binding.setCcvm(viewModel);

        return binding.getRoot();
    }

    @Override
    public void createChallenge(Challenge challenge) {

        //TODO: Move this network call in different file
        APIClient.getClient().createChallange(challenge, new APIClient.CreateChallengeListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getContext(),
                        "Success in creating challenge",Toast.LENGTH_LONG).show();
                getFragmentManager().beginTransaction().replace(R.id.frame,
                        new CurrentChallenges()).commit();
            }

            @Override
            public void onFailure(String error_message) {
                Log.d("DEBUG", "Failure in creating challenge");
            }
        });


    }

}
