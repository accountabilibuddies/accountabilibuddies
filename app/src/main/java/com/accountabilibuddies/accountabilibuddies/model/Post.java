package com.accountabilibuddies.accountabilibuddies.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

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
        //ParseUser  owner; Since Parse already stores the owner of the object
        String imageUrl; --> Url to Image
        String postText; --> If post is plain text
        String videoUrl; --> Url to Video
        //ParseGeoPoint point;
        Double latitude,
        Double longitude;
        List<Comment> comments; <Comment>
        List<Like> likes; <Like>
     }
     */

    // Default Constructor
    public Post() {
        super();
    }

    //This constructor is only for testing purpose and should be removed in Production environment
    public Post(int type, String imageUrl, String postText, String videoUrl, Double latitude, Double longitude) {
        super();
        setType(type);
        setImageUrl(imageUrl);
        setText(postText);
        setVideoUrl(videoUrl);
        setLatitude(latitude);
        setLongitude(longitude);
        //setLocation(point);
        List<Comment> comments = new ArrayList<>();
        setCommentList(comments);
        //List<Like> likes = new ArrayList<>();
        //setLikeList(like);
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

/*
    public ParseGeoPoint getLocation() {return (ParseGeoPoint) get("Location");}

    public void setLocation(ParseGeoPoint location) {
        if (location != null)
            put("Location", location);
    }
*/
    public List<Comment> getCommentList() {
        return (List<Comment>) get("commentList");
    }

    public void setCommentList(List<Comment> commentList) {
        put("commentList", commentList);
    }
}
