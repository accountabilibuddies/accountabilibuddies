package com.accountabilibuddies.accountabilibuddies.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.application.ParseApplication;
import com.accountabilibuddies.accountabilibuddies.databinding.ActivityCreateChallengeBinding;
import com.accountabilibuddies.accountabilibuddies.fragments.AddFriendsFragment;
import com.accountabilibuddies.accountabilibuddies.model.Challenge;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.accountabilibuddies.accountabilibuddies.util.CameraUtils;
import com.accountabilibuddies.accountabilibuddies.util.Constants;
import com.accountabilibuddies.accountabilibuddies.util.DateUtils;
import com.parse.ParseCloud;
import com.parse.ParseUser;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class CreateChallengeActivity extends AppCompatActivity {

    private ActivityCreateChallengeBinding binding;
    private static int CHALLENGE_TYPE = Constants.TYPE_ONE_ON_ONE;
    private String mImagePath, profileUrl;
    private static final int PHOTO_INTENT_REQUEST = 100;
    private static final int REQUEST_CAMERA = 1;

    private AddFriendsFragment addFriendsFragment;

    private static int freq = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_challenge);

        getWindow().getDecorView().setBackground(getResources().getDrawable(R.drawable.background));

        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.close));

        setupViews();
    }

    private void setupViews() {
        binding.tvFrequency.setText(Constants.frequencyMap.get(freq));
        setUpFriendsView();
    }

    private void setUpFriendsView() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        addFriendsFragment = AddFriendsFragment.newInstance(null);
        ft.replace(R.id.flAddFriends, addFriendsFragment);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_save:
                createChallenge();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_menu, menu);
        return true;
    }


    public void launchCamera(View view) {
        if (CameraUtils.cameraPermissionsGranted(this)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        } else {
            launchCameraWithPermissionGranted();
        }
    }

    private void clickChallengePictureIntent(Intent intent) {
        File imageFile = null;
        try {
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            imageFile = CameraUtils.createImageFile(storageDir);
        } catch (IOException ex) {
            Snackbar.make(binding.cLayout, "Image file not created", Snackbar.LENGTH_LONG).show();
        }

        if (imageFile != null) {
            mImagePath = imageFile.getAbsolutePath();
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.accountabilibuddies.accountabilibuddies.fileprovider",
                    imageFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent, PHOTO_INTENT_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        binding.progressBarContainer.setVisibility(View.VISIBLE);
//        binding.avi.show();

        switch (requestCode) {
            case PHOTO_INTENT_REQUEST:
                if (resultCode == RESULT_OK) {
                    if (mImagePath == null) {
//                        binding.avi.hide();
//                        binding.progressBarContainer.setVisibility(View.GONE);
                        return;
                    }
                    Bitmap bitmap = BitmapFactory.decodeFile(mImagePath);

                    APIClient.getClient().uploadFile("post_image.jpg",
                            bitmap,new APIClient.UploadFileListener() {
                                @Override
                                public void onSuccess(String fileLocation) {
                                    profileUrl = fileLocation;
                                    //Show image in the ImageView
//                            binding.ivProfile.setImageBitmap(bitmap);
//                            binding.avi.hide();
//                            binding.progressBarContainer.setVisibility(View.GONE);
                                }

                                @Override
                                public void onFailure(String error_message) {
//                            binding.avi.hide();
//                            binding.progressBarContainer.setVisibility(View.GONE);
                                }
                            });
                }
        }
    }

    public void createChallengeNotification(String channel, String challenger,
                                            String challengeId, int challengeType) {

        HashMap<String, String> notification = new HashMap<>();
        notification.put("text", channel);
        notification.put("channel", channel);
        notification.put("challenger", challenger);
        notification.put("challengeId", challengeId);
        notification.put("challengeType",String.valueOf(challengeType));
        ParseCloud.callFunctionInBackground("androidPushTest", notification);
    }

    @SuppressWarnings("all")
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if(requestCode == REQUEST_CAMERA) {
            if(grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCameraWithPermissionGranted();
            } else {
                Toast.makeText(this, "Cannot add a photo without permissions",Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void launchCameraWithPermissionGranted() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            clickChallengePictureIntent(intent);
        }
    }

    private void createChallenge() {

        if (!dataValidation())
            return;

        Challenge challenge = new Challenge(CHALLENGE_TYPE, binding.etTitle.getText().toString(),
                binding.etDescription.getText().toString(),
                new Date(String.valueOf(binding.tvStartDate.getText())),
                new Date(String.valueOf(binding.tvEndDate.getText())),
                freq, profileUrl, 0,
                addFriendsFragment.getSelectedFriends());//There is no category so pass 0

        APIClient.getClient().createChallenge(challenge, new APIClient.ChallengeListener() {
            @Override
            public void onSuccess() {
                Intent intent;
                if(challenge.getType()== Constants.TYPE_ONE_ON_ONE) {
                    intent = new Intent(CreateChallengeActivity.this, ChallengeOneOnOneActivity.class);
                } else {
                    intent = new Intent(CreateChallengeActivity.this, ChallengeDetailsActivity.class);
                }
                intent.putExtra("challengeId", challenge.getObjectId());
                intent.putExtra("name", challenge.getName());

                String currUser = (String) ParseApplication.getCurrentUser().get("name");
                List<ParseUser> friends = challenge.getUserList();

                for(ParseUser friend : friends) {
                    if(!friend.get("name").equals(currUser)) {
                        createChallengeNotification((String)friend.get("name"),
                                currUser, challenge.getObjectId(), challenge.getType());
                    }
                }
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(String error_message) {
                Log.d("DEBUG", "Failure in creating challenge");
            }
        });
    }

    private boolean dataValidation() {

        if (binding.etTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title is empty", Toast.LENGTH_LONG).show();
            return false;
        }

        if (binding.etDescription.getText().toString().isEmpty()) {
            Toast.makeText(this, "Description is empty", Toast.LENGTH_LONG).show();
            return false;
        }

        return false;
    }


    public void setStartDate(View view) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        // Create a date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                R.style.AppTheme, datePickerListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.setCancelable(true);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        Window window = datePickerDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener datePickerListener =
            (view, year, month, dayOfMonth) -> {
                String startDate = DateUtils
                        .createSelectedDateString(year, dayOfMonth, month);
                binding.tvStartDate.setText(startDate);
            };

    public void setEndDate(View view) {

        if(binding.tvStartDate.getText().length() == 0) {
            Toast.makeText(view.getContext(),
                    "Please select start date first!", Toast.LENGTH_SHORT).show();
            return;
        }

        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                R.style.AppTheme, endDatePickerListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.setCancelable(true);

        cal.add(Calendar.DAY_OF_YEAR, 1);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        Window window = datePickerDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        datePickerDialog.show();
    };

    private DatePickerDialog.OnDateSetListener endDatePickerListener =
            (view, year, month, dayOfMonth) -> {
                String endDate = DateUtils
                        .createSelectedDateString(year, dayOfMonth, month);
                binding.tvEndDate.setText(endDate);
            };


    public void incFrequency(View v) {
        if(freq>=Constants.frequencyMap.size()) {
            return;
        } else {
            freq++;
            binding.tvFrequency.setText(Constants.frequencyMap.get(freq));
        }
    }

    public void decFrequency(View v) {
        if(freq<=1) {
            return;
        } else {
            freq--;
            binding.tvFrequency.setText(Constants.frequencyMap.get(freq));
        }
    }
}

