package com.accountabilibuddies.accountabilibuddies.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.databinding.ActivityHolderBinding;
import com.accountabilibuddies.accountabilibuddies.fragments.CommentsFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class HolderActivity extends AppCompatActivity {

    ActivityHolderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_holder);

        setUpToolbar();

        String fragType = getIntent().getStringExtra("frag_type");
        String postId = getIntent().getStringExtra("postId");

        FragmentManager fm = getSupportFragmentManager();
        switch (fragType) {

            case "comments":
                fm.beginTransaction()
                        .replace(R.id.holder, CommentsFragment.getInstance(postId))
                        .commit();
                getSupportActionBar().setTitle("Comments");
                break;
            default:
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpToolbar() {

        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().getDecorView().setBackground(getResources().getDrawable(R.drawable.background));
    }
}
