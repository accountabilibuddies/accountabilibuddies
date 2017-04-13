package com.accountabilibuddies.accountabilibuddies.util;

import java.util.HashMap;
import java.util.Map;

abstract public class Constants {
    // Challenge types, MVP will use only one type of challenge
    public static final int TYPE_ONE_ON_ONE = 1;
    public static final int TYPE_MULTI_USER = 2;
    //public static final int TYPE_TIMELINE = 3;

    // Challenge Categories,
    public static final int CATEGORY_PHOTOGRAPHY = 1;
    public static final int CATEGORY_FITNESS = 2;
    public static final int CATEGORY_COMMUNITY_SERVICE = 3;
    public static final int CATEGORY_FOOD = 4;
    public static final int CATEGORY_NEW_HOBBY = 5;
    public static final int CATEGORY_BOOKS = 6;
    // Can add more category types
    public static final int CATEGORY_CUSTOM = 99;

    // Challenge Frequency,
    public static final int FREQUENCY_DAILY = 1;
    public static final int FREQUENCY_WEEKLY = 2;
    public static final int FREQUENCY_TWICE_A_MONTH = 3;
    public static final int FREQUENCY_MONTHLY = 4;
    //public static final int FREQUENCY_FIVE_TIMES = 5;
    //public static final int FREQUENCY_SIX_TIMES = 6;
    //public static final int FREQUENCY_ALL_WEEK = 7;

    // Post Type
    public static final int TYPE_VIDEO = 1;
    public static final int TYPE_IMAGE = 2;
    public static final int TYPE_TEXT = 3;
    public static final int TYPE_LOCATION = 4;

    public static Map<String,Integer> categoryIdMap = new HashMap<>();
    static {
        categoryIdMap.put("Photography",1);
        categoryIdMap.put("Fitness",2);
    }
}
