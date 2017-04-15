package com.accountabilibuddies.accountabilibuddies.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.databinding.FragmentAddFriendsBinding;
import com.accountabilibuddies.accountabilibuddies.model.Friend;
import com.accountabilibuddies.accountabilibuddies.viewmodel.AddFriendsViewModel;
import com.parse.ParseUser;

import java.util.List;

public class AddFriendsFragment extends Fragment {

    private FragmentAddFriendsBinding binding;
    private AddFriendsViewModel viewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        viewModel = new AddFriendsViewModel(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_friends, parent, false);
        binding.setAddFriendsViewModel(viewModel);

        setUpFriendsView();

        return binding.getRoot();
    }

    private void setUpFriendsView() {

        viewModel.showFriendsView(binding.actvFriends);

        binding.actvFriends.setOnTouchListener(
            (View view, MotionEvent event) -> {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    binding.actvFriends.showDropDown();
                    binding.actvFriends.requestFocus();
                }

                return true;
            }
        );

        binding.actvFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseUser friend = viewModel.getFriend(position);
                viewModel.addFriend(friend);
            }
        });
    }

    public List<ParseUser> getSelectedFriends() {

        return viewModel.getSelectedFriends();
    }
}
