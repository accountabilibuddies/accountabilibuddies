package com.accountabilibuddies.accountabilibuddies.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.databinding.DetailLocationBinding;
import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class PostWithLocationFragment extends Fragment {

    DetailLocationBinding binding;
    private GoogleMap map;

    public static PostWithLocationFragment newInstance(String postId) {

        PostWithLocationFragment fragment = new PostWithLocationFragment();

        Bundle args = new Bundle();
        args.putString("postId", postId);

        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.detail_location, parent, false);

        String postId = getArguments().getString("postId");

        APIClient.getClient().getPostById(postId, new APIClient.GetPostListener() {

            @Override
            public void onSuccess(Post post) {
                setUpMap(post);
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(getContext(), "Failed to get post " + postId, Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    public void setUpMap(Post post) {

        binding.mapview.onCreate(null);
        binding.mapview.getMapAsync(

            (GoogleMap googleMap) -> {
                MapsInitializer.initialize(getContext());
                map = googleMap;

                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                //set map location
                LatLng location = new LatLng(post.getLatitude(), post.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13f));
                MarkerOptions marker = new MarkerOptions()
                                .position(location);

                if (post.getAddress() != null)
                    marker.title(post.getAddress());
                Marker mark = map.addMarker(marker);
                mark.showInfoWindow();
            }
        );
    }
}
