package com.accountabilibuddies.accountabilibuddies.viewmodel;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.BaseObservable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.model.Challenge;
import com.accountabilibuddies.accountabilibuddies.util.Constants;
import com.accountabilibuddies.accountabilibuddies.util.DateUtils;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CreateChallengeViewModel extends BaseObservable {

    public interface CreateChallengeDataListener {
        void createChallenge(Challenge challenge);
    }

    private static final String TAG = "CreateChallengeViewModel";

    private CreateChallengeDataListener listener;
    private Challenge challenge;
    private Context context;

    public CreateChallengeViewModel(CreateChallengeDataListener listener, Context context) {
        this.listener = listener;
        this.context = context;
        this.challenge = new Challenge();
        //Add the ower to userlist
        List<ParseUser> users = new ArrayList<>();
        users.add(ParseUser.getCurrentUser()); //Add the creater as the participant
        this.challenge.setUserList(users);
    }

    public String getName() {
        return challenge.getName();
    }

    public void setName(String name) {
        challenge.setName(name);
        notifyChange();
    }

    public TextWatcher getChallengeName() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                setName(editable.toString());
            }
        };
    }

    public String getDescription() {
        return challenge.getDescription();
    }

    public void setDescription(String description) {
        challenge.setDescription(description);
        notifyChange();
    }

    public TextWatcher getChallengeDescription() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                setDescription(editable.toString());
            }
        };
    }


    public String getStartDate() {
        return challenge.getStartDate()!=null? challenge.getStartDate().toString():"";
    }

    public void setStartDate(String startDate) {
        challenge.setStartDate(new Date((startDate)));
        notifyChange();
    }

    public String getEndDate() {
        return challenge.getEndDate()!=null? challenge.getEndDate().toString():"";
    }

    public void setEndDate(String endDate) {
        challenge.setEndDate(new Date((endDate)));
        notifyChange();
    }

    public String getFrequency() {
        return challenge.getFrequency()!=null?
                String.valueOf(challenge.getFrequency()): "0";
    }

    public void setFrequency(String freq) {
        challenge.setFrequency(Integer.valueOf(freq));
        notifyChange();
    }

    public View.OnClickListener onShowoffClick() {
        return view -> {
            Toast.makeText(view.getContext(),
                    "Clicked ShowOff ", Toast.LENGTH_LONG).show();
        };
    }

    public View.OnClickListener onMotivationClick() {
        return view -> {
            Toast.makeText(view.getContext(),
                    "Clicked Motivation ", Toast.LENGTH_LONG).show();
        };
    }

    public View.OnClickListener onBtnMinusClick() {
        return  view -> {
            if(Integer.valueOf(getFrequency())>0) {
                setFrequency(String.valueOf(Integer.valueOf(getFrequency())-1));
            }
        };
    }

    public View.OnClickListener onBtnPlusClick() {
        return  view -> {
            setFrequency(String.valueOf(Integer.valueOf(getFrequency())+1));
        };
    }

    //TODO: Move this to common code as per MVVM
    public View.OnClickListener selectStartDate() {
        return view -> {

            //TODO: Move this to the view code
            Calendar cal = Calendar.getInstance(TimeZone.getDefault());
            // Create a date picker dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(context,
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

        };
    }

    private DatePickerDialog.OnDateSetListener datePickerListener =
            (view, year, month, dayOfMonth) -> {
                String startDate = DateUtils
                        .createSelectedDateString(year, dayOfMonth, month);
                setStartDate(startDate);
            };

    public View.OnClickListener selectEndDate() {
        return view -> {

            if(getStartDate()==null) {
                Toast.makeText(view.getContext(),
                        "Please select start date first!", Toast.LENGTH_SHORT).show();
                return;
            }

            //TODO: Move this to the view code
            Calendar cal = Calendar.getInstance(TimeZone.getDefault());
            // Create a date picker dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                    R.style.AppTheme, endDatePickerListener,
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
        };
    }

    private DatePickerDialog.OnDateSetListener endDatePickerListener =
            (view, year, month, dayOfMonth) -> {
                String endDate = DateUtils
                        .createSelectedDateString(year, dayOfMonth, month);
                setEndDate(endDate);
            };


    public View.OnClickListener onCreateClicked() {
        return view -> {
            //TODO: Remove this later
            Toast.makeText(view.getContext(),
                    "Debug " + getName(), Toast.LENGTH_SHORT).show();

            //TODO: Add all the validations
            challenge.setCategory(Constants.categoryIdMap.get("Fitness"));

            listener.createChallenge(challenge);
        };

    }

}
