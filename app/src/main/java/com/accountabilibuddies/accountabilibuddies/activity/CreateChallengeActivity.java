package com.accountabilibuddies.accountabilibuddies.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.databinding.ActivityCreateChallengeBinding;
import com.accountabilibuddies.accountabilibuddies.model.Challenge;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.accountabilibuddies.accountabilibuddies.util.CameraUtils;
import com.accountabilibuddies.accountabilibuddies.util.Constants;
import com.accountabilibuddies.accountabilibuddies.util.DateUtils;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class CreateChallengeActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    private ActivityCreateChallengeBinding binding;
    private static int CHALLENGE_TYPE = Constants.TYPE_ONE_ON_ONE;
    private static int CHALLENGE_FREQUENCY = Constants.FREQUENCY_DAILY;
    private String mImagePath, profileUrl;
    private static final int PHOTO_INTENT_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_challenge);

        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.close));

        setupDateTime();
        SetupChallengeType();
        setupFrequency();
    }

    private void setupFrequency() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.frequency_type,
                        android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spFrequency.setAdapter(adapter);
        binding.spFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        CHALLENGE_FREQUENCY = Constants.FREQUENCY_DAILY;
                        break;
                    case 1:
                        CHALLENGE_FREQUENCY = Constants.FREQUENCY_WEEKLY;
                        break;
                    case 2:
                        CHALLENGE_FREQUENCY = Constants.FREQUENCY_TWICE_A_MONTH;
                        break;
                    case 3:
                        CHALLENGE_FREQUENCY = Constants.FREQUENCY_MONTHLY;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void SetupChallengeType() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.challenge_type,
                        android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spType.setAdapter(adapter);
        binding.spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        CHALLENGE_TYPE = Constants.TYPE_ONE_ON_ONE;
                        break;
                    case 1:
                        CHALLENGE_TYPE = Constants.TYPE_MULTI_USER;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupDateTime() {
        Calendar calendar = Calendar.getInstance();

        binding.tvStartDate.setText(DateUtils.getDate(calendar));
        binding.tvStartTime.setText(DateUtils.getTime(calendar));

        calendar.add(Calendar.DAY_OF_YEAR, 1);

        binding.tvEndDate.setText(DateUtils.getDate(calendar));
        binding.tvEndTime.setText(DateUtils.getTime(calendar));
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

    private void createChallenge() {

        if (!dataValidation())
            return;

        Challenge challenge = new Challenge(CHALLENGE_TYPE, binding.etTitle.getText().toString(),
                                        binding.etDescription.getText().toString(),
                                        new Date(String.valueOf(binding.tvStartDate.getText())),
                                        new Date(String.valueOf(binding.tvEndDate.getText())),
                                        CHALLENGE_FREQUENCY, profileUrl, 0);//There is no category so pass 0

        APIClient.getClient().createChallenge(challenge, new APIClient.ChallengeListener() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(CreateChallengeActivity.this, ChallengeDetailsActivity.class);
                intent.putExtra("challengeId", challenge.getObjectId());
                intent.putExtra("name", challenge.getName());
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
            Snackbar.make(binding.cLayout, "Title is empty", Snackbar.LENGTH_LONG).show();
            return false;
        }

        if (binding.etDescription.getText().toString().isEmpty()) {
            Snackbar.make(binding.cLayout, "Description is empty", Snackbar.LENGTH_LONG).show();
            return false;
        }

        Date startDate = new Date(String.valueOf(binding.tvStartDate.getText()));
        Date endDate = new Date(String.valueOf(binding.tvEndDate.getText()));
        Date today = new Date();

        if (today.compareTo(startDate) > 0 )  {
            Snackbar.make(binding.cLayout, "Start date is in past", Snackbar.LENGTH_LONG).show();
            return false;
        }

        if (startDate.compareTo(endDate) > 0 )  {
            Snackbar.make(binding.cLayout, "End Date is before start date", Snackbar.LENGTH_LONG).show();
            return false;
        }
        // if no challenge image use dafault image

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_menu, menu);
        return true;
    }

    public void onTimeClick(View view) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                CreateChallengeActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setOnCancelListener(dialogInterface -> Log.d("TimePicker", "Dialog was cancelled"));
        tpd.show(getFragmentManager(), "Select Time Range");
    }

    public void onDateClick(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                CreateChallengeActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Select Date Range");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth, 0, 0);
        binding.tvStartDate.setText(DateUtils.getDate(c));

        c.set(yearEnd, monthOfYearEnd, dayOfMonthEnd, 0, 0);
        binding.tvEndDate.setText(DateUtils.getDate(c));
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        Calendar c = Calendar.getInstance();
        c.set(0, 0, 0, hourOfDay, minute);
        binding.tvStartTime.setText(DateUtils.getTime(c));

        c.set(0, 0, 0, hourOfDayEnd, minuteEnd);
        binding.tvEndTime.setText(DateUtils.getTime(c));
    }

    public void launchCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            clickChallengePictureIntent(intent);
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

        switch (requestCode) {
            case PHOTO_INTENT_REQUEST:
                if (resultCode == RESULT_OK) {
                    if (mImagePath == null)
                        return;

                    Bitmap bitmap = BitmapFactory.decodeFile(mImagePath);

                        APIClient.getClient().uploadFile("post_image.jpg",
                                    bitmap,new APIClient.UploadFileListener() {
                        @Override
                        public void onSuccess(String fileLocation) {
                            profileUrl = fileLocation;
                            //Show image in the ImageView
                            binding.ivProfile.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onFailure(String error_message) {

                        }
                    });
                }
        }
    }
}
