package com.accountabilibuddies.accountabilibuddies.model;

import com.accountabilibuddies.accountabilibuddies.application.ParseApplication;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@ParseClassName("Challenge")
public class Challenge extends ParseObject {

    //Default Parse columns
    // objectId is a unique identifier for each saved object.
    // createdAt and updatedAt represent the time that each object was created and last modified.
    /**
     Challenge {
        int typeId;
        ParseUser owner;
        String name;
        String description;
        String startDate;
        String endDate;
        int frequency;
        String imageUrl;
        int categoryId;
        List of user; <ParseUser>
        List of post; <Post>
        List of Scoreboard;
     }
     */

    // Default Constructor
    public Challenge() {
        super();
    }

    //This constructor is only for testing purpose and should be removed in Production environment
    public Challenge(int typeId, String name, String description, Date startDate,
                     Date endDate,  int frequency, String imageUrl, int categoryId,
                     Set<ParseUser> friends) {
        super();
        setType(typeId);
        setOwner(ParseApplication.getCurrentUser());
        setName(name);
        setDescription(description);
        setStartDate(startDate);
        setEndDate(endDate);
        setFrequency(frequency);
        setImageUrl(imageUrl);
        setCategory(categoryId);
        List<ParseUser> users = new ArrayList<>();
        users.add(ParseApplication.getCurrentUser()); //Add the creator as the participant
        users.addAll(friends);
        setUserList(users);
        List<Post> posts = new ArrayList<>();
        setPostList(posts);
        List<Scoreboard> scoreboard = new ArrayList<>();
        Scoreboard sc = new Scoreboard(ParseUser.getCurrentUser());
        scoreboard.add(sc);
        setScoreboard(scoreboard);
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
        return (List<ParseUser>) get("userList");
    }

    public void setUserList(List<ParseUser> userList) {
        put("userList", userList);
    }

    public List<Post> getPostList() {
        return (List<Post>) get("postList");
    }

    public void setPostList(List<Post > postList) {
        put("postList", postList);
    }

    public ParseUser getOwner() {
        return (ParseUser) get("owner");
    }

    public void setOwner(ParseUser owner) {
        put("owner", owner);
    }

    public String getOwnerProfileImageUrl() {

        try {

            ParseUser owner = getOwner().fetchIfNeeded();
            return owner.getString("profilePhotoUrl");

        } catch (ParseException e) {
            e.printStackTrace();

            return null;
        }
    }

    public List<Scoreboard> getScoreboard() {
        return (List<Scoreboard>) get("scoreboardList");
    }

    public void setScoreboard(List<Scoreboard > scoreboardList) {
        put("scoreboardList", scoreboardList);
    }
}
