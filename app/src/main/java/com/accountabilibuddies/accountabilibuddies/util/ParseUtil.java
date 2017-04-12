package com.accountabilibuddies.accountabilibuddies.util;

import com.parse.ParseUser;

import java.util.List;

public class ParseUtil {

    public static boolean containsUser(List<ParseUser> list, ParseUser user) {
        for (ParseUser parseUser : list) {
            if (parseUser.hasSameId(user)) return true;
        }
        return false;

    }
}
