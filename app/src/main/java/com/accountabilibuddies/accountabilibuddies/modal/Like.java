package com.accountabilibuddies.accountabilibuddies.modal;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Like")
public class Like extends ParseObject {
    //Default Parse columns
    // objectId is a unique identifier for each saved object.
    // createdAt and updatedAt represent the time that each object was created and last modified.
    /**
     Comments {
        ParseUser  user; <ParseUser>
     }
     */

    // Default Constructor
    public Like() {
        super();
    }

    public ParseUser getUser() {
        return (ParseUser ) get("user");
    }

    public void setUser(ParseUser user) {
        put("user", user);
    }
}
