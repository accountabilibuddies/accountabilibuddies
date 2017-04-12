package com.accountabilibuddies.accountabilibuddies.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.location.Location;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.adapter.PostAdapter;
import com.accountabilibuddies.accountabilibuddies.databinding.ActivityChallengeDetailsBinding;
import com.accountabilibuddies.accountabilibuddies.fragments.PostTextFragment;
import com.accountabilibuddies.accountabilibuddies.model.Challenge;
import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.accountabilibuddies.accountabilibuddies.util.CameraUtils;
import com.accountabilibuddies.accountabilibuddies.util.Constants;
import com.accountabilibuddies.accountabilibuddies.util.ItemClickSupport;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.parse.ParseObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChallengeDetailsActivity extends AppCompatActivity
        implements PostTextFragment.PostTextListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private ActivityChallengeDetailsBinding binding;
    APIClient client;
    protected ArrayList<Post> mPostList;
    protected PostAdapter mAdapter;
    protected LinearLayoutManager mLayoutManager;
    private GoogleApiClient mGoogleApiClient;
    private Challenge challenge;
    private Double mLatitude;
    private Double mLongitude;

    private static final int PHOTO_INTENT_REQUEST = 100;
    private static final int REQUEST_LOCATION = 1;
    private String mImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_challenge_details);

        challenge = ParseObject.createWithoutData(Challenge.class,
                getIntent().getStringExtra("challengeId"));

        //Setting toolbar
        setSupportActionBar(binding.toolbar);

        // Display icon in the toolbar
        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Client instance
        client = APIClient.getClient();

        //Google client for location
        setupGoogleClient();

        mPostList = new ArrayList<>();
        mAdapter = new PostAdapter(this, mPostList);
        binding.rVPosts.setAdapter(mAdapter);
        binding.rVPosts.setItemAnimator(new DefaultItemAnimator());

        //Recylerview decorater
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        binding.rVPosts.addItemDecoration(itemDecoration);

        mLayoutManager = new LinearLayoutManager(this);
        binding.rVPosts.setLayoutManager(mLayoutManager);

        //Swipe to refresh
        binding.swipeContainer.setOnRefreshListener(() -> {
            getPosts();
        });

        ItemClickSupport.addTo(binding.rVPosts).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {


            }
        });

        getPosts();
    }

    private void setupGoogleClient() {
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void getPosts() {
        client.getPostList(challenge.getObjectId(), new APIClient.GetPostListListener(){
            @Override
            public void onSuccess(List<Post> postList) {
                if (postList != null) {
                    mPostList.clear();
                    mPostList.addAll(postList);
                    mAdapter.notifyDataSetChanged();
                }
                binding.swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(String error_message) {
                binding.swipeContainer.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void closeFabMenu(){
        binding.fabMenu.close(true);
    }

    public void launchCamera(View view) {
        closeFabMenu();

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            dispatchTakePictureIntent(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case PHOTO_INTENT_REQUEST:

                if (resultCode == RESULT_OK) {
                    if(mImagePath==null) {
                        //TODO: Handle error
                        return;
                    }
                    //TODO: Need to optimize this scale to make image size more efficient
                    // wrt to the image view size
                    Bitmap bitmap = CameraUtils.scaleToFill(BitmapFactory.decodeFile(mImagePath)
                            ,800,600);

                    client.uploadFile("post_image.jpg",
                            bitmap, new APIClient.UploadFileListener() {
                                @Override
                                public void onSuccess(String fileLocation) {
                                    Post post = new Post();
                                    post.setType(Constants.TYPE_IMAGE);
                                    post.setImageUrl(fileLocation);

                                    Log.d("Objectid", challenge.getObjectId());

                                    //TODO: Move the listener out of this function
                                    APIClient.getClient().createPost(post, challenge.getObjectId(),
                                            new APIClient.PostListener() {
                                                @Override
                                                public void onSuccess() {
                                                    Toast.makeText(ChallengeDetailsActivity.this,
                                                            "Creating post", Toast.LENGTH_LONG).show();
                                                    onCreatePost(post);
                                                }

                                                @Override
                                                public void onFailure(String error_message) {
                                                    Toast.makeText(ChallengeDetailsActivity.this,
                                                            "Error creating post", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                }

                                @Override
                                public void onFailure(String error_message) {

                                }
                            });
                }
        }
    }

    /**
     * Function to launch a dialog fragment to post text
     * @param view
     */
    public void launchTextPost(View view) {

        closeFabMenu();

        FragmentManager fm = getSupportFragmentManager();
        PostTextFragment fragment = PostTextFragment.getInstance(challenge.getObjectId());
        fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        fragment.show(fm, "post_text");
    }

    /**
     *  Function to add post to the posts list.
     *  //TODO: Change this based on in what order the list is to be shown
     *
     */
    void onCreatePost(Post post) {
        mPostList.add(post);
        mAdapter.notifyDataSetChanged();
        mLayoutManager.scrollToPosition(mPostList.size() - 1);
        Log.d("File count", String.valueOf(mPostList.size()));
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onFinishPost(Post post) {
        onCreatePost(post);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION },
                    REQUEST_LOCATION);
        } else {
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                mLatitude = mLastLocation.getLatitude();
                mLongitude = mLastLocation.getLongitude();
            }
        }
    }
    @SuppressWarnings("all")
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if(grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // We can now safely use the API we requested access to
            } else {
                // Permission was denied or request was cancelled
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void shareLocation(View view) {
        closeFabMenu();
        //ParseGeoPoint point = new ParseGeoPoint(mLatitude, mLongitude);

        Post post = new Post();
        post.setType(Constants.TYPE_LOCATION);
        //post.setLocation(point);
        post.setLatitude(mLatitude);
        post.setLongitude(mLongitude);

        APIClient.getClient().createPost(post, challenge.getObjectId(),
            new APIClient.PostListener() {

                @Override
                public void onSuccess() {
                    Toast.makeText(ChallengeDetailsActivity.this,
                            "Creating post", Toast.LENGTH_LONG).show();
                    onCreatePost(post);
                }

                @Override
                public void onFailure(String error_message) {
                    Toast.makeText(ChallengeDetailsActivity.this,
                            "Error creating post", Toast.LENGTH_LONG).show();
                }
            }
        );
    }
    /**
     * Function create an image file to be used by the camera app to store the
     * image. Intent is then dispatched after the local image path is stored
     * which later will be used to upload the file
     * @param intent
     */
    private void dispatchTakePictureIntent(Intent intent) {

        File imageFile = null;
        try {
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            imageFile = CameraUtils.createImageFile(storageDir);
        } catch (IOException ex) {
            //TODO: Handle error
        }
        // Continue only if the File was successfully created
        if (imageFile != null) {
            //This path will be used to save in the post
            mImagePath = imageFile.getAbsolutePath();
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.accountabilibuddies.accountabilibuddies.fileprovider",
                    imageFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent, PHOTO_INTENT_REQUEST);
        }
    }
}
