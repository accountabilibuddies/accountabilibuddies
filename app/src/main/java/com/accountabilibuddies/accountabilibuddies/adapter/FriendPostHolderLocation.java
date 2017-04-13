package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.accountabilibuddies.accountabilibuddies.util.DateUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendPostHolderLocation extends RecyclerView.ViewHolder {

    @BindView(R.id.friendPostMap)
    MapView mapView;

    @BindView(R.id.friendPostMapDate)
    TextView dateView;

    private GoogleMap map;

    public FriendPostHolderLocation(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void viewBasedOnPost(Post post, Context context) {

        if (post != null) {
            mapView.onCreate(null);
            mapView.getMapAsync(googleMap -> {
                MapsInitializer.initialize(context);
                map = googleMap;

                //set map location
                LatLng location = new LatLng(post.getLatitude(), post.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13f));
                map.addMarker(new MarkerOptions().position(location));
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            });

            if(post.getCreatedAt()!=null) {
                dateView.setText(DateUtils.getTimeFromDate(post.getCreatedAt()));
            }
        }
    }


}
