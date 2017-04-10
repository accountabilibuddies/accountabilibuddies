package com.accountabilibuddies.accountabilibuddies.viewmodel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.accountabilibuddies.accountabilibuddies.adapter.CategoriesAdapter;
import com.accountabilibuddies.accountabilibuddies.model.Category;

public class CategoriesViewModel {

    private Context context;
    private CategoriesAdapter adapter;

    public CategoriesViewModel(Context context) {

        this.context = context;
        this.adapter = new CategoriesAdapter(context);
    }

    public void onCreate() {

        initializeHardcodedCategories();
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public void initializeHardcodedCategories() {

        adapter.categories.addAll(Category.getHardcodedCategories());
        adapter.notifyDataSetChanged();
    }
}
