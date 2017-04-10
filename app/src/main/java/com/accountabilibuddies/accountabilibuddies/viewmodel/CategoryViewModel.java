package com.accountabilibuddies.accountabilibuddies.viewmodel;


import android.content.Context;

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
}
