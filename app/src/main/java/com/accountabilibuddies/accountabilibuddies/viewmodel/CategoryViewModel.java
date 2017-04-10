package com.accountabilibuddies.accountabilibuddies.viewmodel;


import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.accountabilibuddies.accountabilibuddies.model.Category;

public class CategoryViewModel {

    private Context context;
    private Category category;

    public CategoryViewModel(Context context, Category category) {

        this.context = context;
        this.category = category;
    }

    public String getName() {

        return category.getName();
    }

    public void addCategoryForUser(View view) {

        //TODO: Add category for user
        Toast.makeText(context, "Added category", Toast.LENGTH_SHORT).show();
    }
}
