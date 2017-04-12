package com.accountabilibuddies.accountabilibuddies.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Friend")
public class Friend extends ParseObject {

    /*

    Friend {
       String name;
       String facebookId;
       String photoUrl;
       String friendOfId;
    }
    */

    public Friend() {

        super();
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getName() {
        return (String) get("name");
    }

    public void setFacebookId(String facebookId) { put("facebookId", facebookId); }

    public String getFacebookId() {
        return (String) get("facebookId");
    }

    public void setFriendOfId(String friendOfId) { put("friendOfId", friendOfId); }

    public String getFriendOfId() {
        return (String) get("friendOfId");
    }

    public void setPhotoUrl(String photoUrl) {
        put("photoUrl", photoUrl);
    }

    public String getPhotoUrl() {
        return (String) get("photoUrl");
    }
}
