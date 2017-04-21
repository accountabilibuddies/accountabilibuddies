package com.accountabilibuddies.accountabilibuddies.model;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Comment")
public class Comment extends ParseObject {

    //Default Parse columns
    // objectId is a unique identifier for each saved object.
    // createdAt and updatedAt represent the time that each object was created and last modified.
    /**
     Comments {
        String comment;
        ParseUser  user; <ParseUser>
     }
     */

    // Default Constructor
    public Comment() {
        super();
    }

    public ParseUser  getUser() {
        return (ParseUser ) get("user");
    }

    public void setUser(ParseUser user) {
        put("user", user);
    }

    public String getComment() {
        return (String) get("comment");
    }

    public void setText(String comment) {
        put("comment", comment);
    }

    public String getProfileImageUrl() {

        try {

            ParseUser user = (ParseUser) get("user");
            return (String) user.fetchIfNeeded().get("profilePhotoUrl");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
