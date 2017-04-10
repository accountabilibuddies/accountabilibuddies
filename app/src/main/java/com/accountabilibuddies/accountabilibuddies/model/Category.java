package com.accountabilibuddies.accountabilibuddies.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Arrays;
import java.util.List;

@ParseClassName("Category")
public class Category extends ParseObject {
    public static final String PLURAL = "categories";

    /*

     Category {
        String name;
     }
     */

    public Category() {

        super();
    }

    public Category(String name) {

        super();
        setName(name);
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getName() {
        return (String) get("name");
    }

    public static List<Category> getHardcodedCategories() {

        List<Category> categories =
            Arrays.asList(
                    new Category("Sports"),
                    new Category("Photography"),
                    new Category("Music"),
                    new Category("Writing"),
                    new Category("Reading"),
                    new Category("Cooking"),
                    new Category("Gardening"),
                    new Category("Fashion"),
                    new Category("Yoga"),
                    new Category("Dance"),
                    new Category("Weight Loss"),
                    new Category("Fashion"),
                    new Category("Programming"),
                    new Category("Finance"),
                    new Category("DIY"),
                    new Category("Crafting")
            );

        deleteAllInBackground(categories);
        saveAllInBackground(categories);

        return categories;
    }
}