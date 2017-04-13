package com.accountabilibuddies.accountabilibuddies.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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
import com.accountabilibuddies.accountabilibuddies.util.DateUtils;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;

import java.util.Calendar;

public class CreateChallengeActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    private ActivityCreateChallengeBinding binding;

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

        Challenge challenge = new Challenge();

        //TODO: Set Challenge parameters

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
        //Check for
        // null Title Description
        // start date should not be less than today
        // End date/time is more than start date
        // if no challenge image use dafault image

        return false;
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
        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });
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
        //Update Start and End Date
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth, 0, 0);
        binding.tvStartDate.setText(DateUtils.getDate(c));

        c.set(yearEnd, monthOfYearEnd, dayOfMonthEnd, 0, 0);
        binding.tvEndDate.setText(DateUtils.getDate(c));
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        //Update Start and End Time
        Calendar c = Calendar.getInstance();
        c.set(0, 0, 0, hourOfDay, minute);
        binding.tvStartTime.setText(DateUtils.getTime(c));

        c.set(0, 0, 0, hourOfDayEnd, minuteEnd);
        binding.tvEndTime.setText(DateUtils.getTime(c));
    }
}
