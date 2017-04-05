package com.accountabilibuddies.accountabilibuddies.modal;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;
import java.util.List;

@ParseClassName("Challenge")
public class Challenge extends ParseObject {

    //Default Parse columns
    // objectId is a unique identifier for each saved object.
    // createdAt and updatedAt represent the time that each object was created and last modified.
    /**
     Challenge {
         long typeId -->Another table
         String name;
         String description;
         String startDate;
         String endDate;
         int frequency; -->Another table
         String imageUrl;
         int categoryId; -->Another table
         List of useruid; <ParseUser>
         List of post; <Post>
     }
     */

    // Default Constructor
    public Challenge() {
        super();
    }

    public String getName() {
        return (String) get("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getDescription() {
        return (String) get("description");
    }

    public void setDescription(String description) {
        put("description", description);
    }

    public Date getStartDate() {
        return (Date) get("startDate");
    }

    public void setStartDate(Date startDate) {
        put("startDate", startDate);
    }

    public Date getEndDate() {
        return (Date) get("endDate");
    }

    public void setEndDate(Date endDate) {
        put("endDate", endDate);
    }

    public int getFrequency() {
        return (int) get("frequency");
    }

    public void setFrequency(int frequency) {
        put("frequency", frequency);
    }

    public String getImageUrl() {
        return (String) get("imageUrl");
    }

    public void setImageUrl(String imageUrl) {
        put("imageUrl", imageUrl);
    }

    public int getCategory() {
        return (int) get("category");
    }

    public void setCategory(int category) {
        put("category", category);
    }

    public List<ParseUser> getUserList() {
        return (List<ParseUser >) get("userList");
    }

    public void setUserList(List<ParseUser > userList) {
        put("userList", userList);
    }

    public List<Post> getPostList() {
        return (List<Post>) get("postList");
    }

    public void setPostList(List<ParseUser > postList) {
        put("postList", postList);
    }
}
