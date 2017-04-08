package com.accountabilibuddies.accountabilibuddies.modal;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ParseClassName("Challenge")
public class Challenge extends ParseObject {

    //Default Parse columns
    // objectId is a unique identifier for each saved object.
    // createdAt and updatedAt represent the time that each object was created and last modified.
    /**
     Challenge {
         int typeId;
         String name;
         String description;
         String startDate;
         String endDate;
         int frequency;
         String imageUrl;
         int categoryId;
         List of user; <ParseUser>
         List of post; <Post>
     }
     */

    // Default Constructor
    public Challenge() {
        super();
        //Adding this a current user is independent of a challenge creation
        List<ParseUser> users = new ArrayList<>();
        users.add(ParseUser.getCurrentUser()); //Add the creater as the participant
        setUserList(users);
    }

    //This constructor is only for testing purpose and should be removed in Production environment
    public Challenge(int typeId, String name, String description, Date startDate,
                     Date endDate,  int frequency, String imageUrl, int categoryId) {
        super();
        setType(typeId);
        setName(name);
        setDescription(description);
        setStartDate(startDate);
        setEndDate(endDate);
        setFrequency(frequency);
        setImageUrl(imageUrl);
        setCategory(categoryId);
        List<ParseUser> users = new ArrayList<>();
        users.add(ParseUser.getCurrentUser()); //Add the creater as the participant
        setUserList(users);
        List<Post> posts = new ArrayList<>();
        setPostList(posts);
    }

    public int getType() {
        return (int) get("type");
    }

    public void setType(int type) {
        put("type", type);
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

    public Integer getFrequency() {
        return (Integer) get("frequency");
    }

    public void setFrequency(int frequency) {
        put("frequency", frequency);
    }

    public String getImageUrl() {
        return (String) get("imageUrl");
    }

    public void setImageUrl(String imageUrl) {
        if (imageUrl != null) //Change this later with a default challenge category icon
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

    public void setPostList(List<Post > postList) {
        put("postList", postList);
    }
}
