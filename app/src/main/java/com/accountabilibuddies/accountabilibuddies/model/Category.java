package com.accountabilibuddies.accountabilibuddies.model;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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
}