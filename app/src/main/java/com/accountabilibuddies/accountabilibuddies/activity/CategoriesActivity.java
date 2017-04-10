package com.accountabilibuddies.accountabilibuddies.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.databinding.ActivityCategoriesBinding;
import com.accountabilibuddies.accountabilibuddies.viewmodel.CategoriesViewModel;

public class CategoriesActivity extends AppCompatActivity {

    private static final int SPAN_COUNT = 2;

    private ActivityCategoriesBinding binding;
    private CategoriesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpViewModel();
        setUpBindings();
        setUpCategoriesView();
    }

    public void setUpViewModel() {

        viewModel = new CategoriesViewModel(CategoriesActivity.this);
        viewModel.onCreate();
    }

    public void setUpBindings() {

        binding = DataBindingUtil.setContentView(CategoriesActivity.this, R.layout.activity_categories);

        binding.setCategoriesViewModel(viewModel);
        binding.executePendingBindings();
    }

    public void setUpCategoriesView() {

//        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoriesActivity.this, DividerItemDecoration.VERTICAL);
//        binding.rvCategories.addItemDecoration(itemDecoration);

        LinearLayoutManager layoutManager = new GridLayoutManager(CategoriesActivity.this, SPAN_COUNT);
        binding.rvCategories.setLayoutManager(layoutManager);

        binding.rvCategories.setAdapter(viewModel.getAdapter());
    }
}