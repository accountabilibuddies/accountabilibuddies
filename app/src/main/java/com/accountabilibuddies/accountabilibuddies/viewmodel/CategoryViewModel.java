package com.accountabilibuddies.accountabilibuddies.viewmodel;


import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.accountabilibuddies.accountabilibuddies.model.Category;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;
import com.parse.ParseUser;

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

        APIClient.getClient().addCategoryForUser(ParseUser.getCurrentUser(), category, new APIClient.AddCategoryListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "Added category", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(context, "Failure to add category", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
