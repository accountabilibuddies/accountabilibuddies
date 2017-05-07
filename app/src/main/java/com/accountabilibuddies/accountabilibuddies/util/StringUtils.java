package com.accountabilibuddies.accountabilibuddies.util;

public class StringUtils {


    public static String generateCommentOwner(String owner) {

        String processedStr = owner.toLowerCase();
        processedStr = processedStr.replaceAll("\\s+","");
        processedStr = processedStr + " ";

        return processedStr;
    }
}
