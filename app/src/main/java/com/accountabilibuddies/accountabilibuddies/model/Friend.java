package com.accountabilibuddies.accountabilibuddies.model;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Friend")
public class Friend extends ParseObject {

    /*

    Friend {
       String username;
       ParseUser friend;
    }
    */

    public Friend() {

        super();
    }

    public void setUsername(String username) {
        put("username", username);
    }

    public String getUsername() {
        return (String) get("username");
    }

    public void setFriend(ParseUser friend) {
        put("friend", friend);
    }

    public ParseUser getFriend() {
        return (ParseUser) get("friend");
    }

    public String getName() {

        try {

            ParseUser friend = (ParseUser) get("friend");
            return (String) friend.fetchIfNeeded().get("name");

        } catch (ParseException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getParseUserId() {

        try {

            ParseUser friend = (ParseUser) get("friend");
            return friend.fetchIfNeeded().getObjectId();

        } catch (ParseException e) {

            e.printStackTrace();
            return null;
        }
    }
}
