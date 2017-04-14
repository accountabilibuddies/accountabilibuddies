package com.accountabilibuddies.accountabilibuddies.model;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

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

    public void setFriend(ParseUser friend) { put("friend", friend); }

    public ParseUser getFriend() { return (ParseUser) get("friend"); }

    public String getName() { return (String) ((ParseUser) get("friend")).get("name"); }
}
