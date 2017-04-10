package com.accountabilibuddies.accountabilibuddies.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.databinding.ItemCategoryBinding;
import com.accountabilibuddies.accountabilibuddies.model.Category;
import com.accountabilibuddies.accountabilibuddies.viewmodel.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Category> categories;
    private Context context;

    public CategoriesAdapter(Context context) {

        this.context = context;
        this.categories = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemCategoryBinding binding = DataBindingUtil
                .inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.item_category,
                        parent,
                        false
                );

        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Category category = categories.get(position);

        CategoryViewHolder categoryHolder = (CategoryViewHolder) holder;
        ItemCategoryBinding binding = categoryHolder.binding;
        CategoryViewModel viewModel = new CategoryViewModel(context, category);

        binding.setCategoryViewModel(viewModel);
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        final ItemCategoryBinding binding;

        public CategoryViewHolder(ItemCategoryBinding itemView) {

            super(itemView.getRoot());

            binding = itemView;
        }
    }
}
