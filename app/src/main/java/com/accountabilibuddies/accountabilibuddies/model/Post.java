package com.accountabilibuddies.accountabilibuddies.model;

import com.accountabilibuddies.accountabilibuddies.application.ParseApplication;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Post")
public class Post extends ParseObject {

    //Default Parse columns
    // objectId is a unique identifier for each saved object.
    // createdAt and updatedAt represent the time that each object was created and last modified.
    /**
     Post {
        int postType;
        //Parse also store created date and last updated date to skipped in schema
        ParseUser owner;
        String imageUrl; --> Url to Image
        String postText; --> If post is plain text
        String videoUrl; --> Url to Video
        Double latitude,
        Double longitude;
        String Address;
        List<Comment> comments; <Comment>
        List<Like> likeList;
     }
     */
    // Default Constructor
    public Post() {
        super();
    }

    //This constructor is only for testing purpose and should be removed in Production environment
    public Post(int type, String imageUrl, String postText, String videoUrl, Double latitude,
                Double longitude, String address) {
        super();
        setType(type);
        setOwner(ParseApplication.getCurrentUser());
        setImageUrl(imageUrl);
        setText(postText);
        setVideoUrl(videoUrl);
        setLatitude(latitude);
        setLongitude(longitude);
        List<Comment> comments = new ArrayList<>();
        setCommentList(comments);
        List<Like> likes = new ArrayList<>();
        setLikeList(likes);
        setAddress(address);
    }

    public int getType() {
        return (int) get("type");
    }

    public void setType(int type) {
        put("type", type);
    }

    public String getImageUrl() {
        return (String)this.get("imageUrl");
    }

    public void setImageUrl(String imageUrl) {
        if (imageUrl != null)
            put("imageUrl", imageUrl);
    }

    public String getText() {
        return (String) get("text");
    }

    public void setText(String text) {
        if (text != null)
            put("text", text);
    }

    public String getVideoUrl() {return (String) get("videoUrl");}

    public void setVideoUrl(String videoUrl) {
        if (videoUrl != null)
            put("videoUrl", videoUrl);
    }

    public Double getLongitude() {
        return (Double) get("longitude");
    }

    public void setLongitude(Double longitude) {
        put("longitude", longitude);
    }

    public Double getLatitude() {
        return (Double) get("latitude");
    }

    public void setLatitude(Double latitude) {
        put("latitude", latitude);
    }

    public List<Comment> getCommentList() {
        return (List<Comment>) get("commentList");
    }

    public void setCommentList(List<Comment> commentList) {
        put("commentList", commentList);
    }

    public List<Like> getLikeList() {
        return (List<Like>) get("likeList");
    }

    public void setLikeList(List<Like> likeList) {
        put("likeList", likeList);
    }

    public ParseUser getOwner() {
        return (ParseUser) get("owner");
    }

    public void setOwner(ParseUser owner) {
        put("owner", owner);
    }

    public String getOwnerName() {

        try {

            ParseUser owner = (ParseUser) get("owner");
            return (String) owner.fetchIfNeeded().get("name");

        } catch (ParseException e) {

            e.printStackTrace();
            return null;
        }
    }

    public String getAddress() {
        return (String) get("address");
    }

    public void setAddress(String address) {
        if (address != null)
            put("address", address);
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
}
