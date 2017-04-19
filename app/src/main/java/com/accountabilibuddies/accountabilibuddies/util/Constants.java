package com.accountabilibuddies.accountabilibuddies.util;

import java.util.HashMap;

abstract public class Constants {

    public static final int TYPE_ONE_ON_ONE = 1;
    public static final int TYPE_MULTI_USER = 2;

    // Post Type
    public static final int TYPE_VIDEO = 1;
    public static final int TYPE_IMAGE = 2;
    public static final int TYPE_TEXT = 3;
    public static final int TYPE_LOCATION = 4;

    public static HashMap<Integer,String> frequencyMap = new HashMap<>();
    static {
        frequencyMap.put(1,"Daily");
        frequencyMap.put(2,"One a week");
        frequencyMap.put(3,"Twice a week");
        frequencyMap.put(4,"Once a Month");
        frequencyMap.put(5,"Twice a Month");
    }
}
