package com.accountabilibuddies.accountabilibuddies.modal;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("Post")
public class Post extends ParseObject {

    //Default Parse columns
    // objectId is a unique identifier for each saved object.
    // createdAt and updatedAt represent the time that each object was created and last modified.
    /**
     Post {
        int postType; -->Another table
        User owner; -->Another table
        String postUrl; --> Url to Audio/Video
        String postText; --> If post is plain text
       //Add a location entry to posts which are just shared locations
        List<Comment> comments; -->Another Table
        List<Like> likes; -->Another table
     }
     */

    // Default Constructor
    public Post() {
        super();
    }

    public int getType() {
        return (int) get("type");
    }

    public void setType(int type) {
        put("type", type);
    }

    public User getUser() {
        return (User) get("user");
    }

    public void setUser(User user) {
        put("user", user);
    }

    public String getText() {
        return (String) get("text");
    }

    public void setText(String text) {
        put("text", text);
    }

    public List<Comment> getCommentList() {
        return (List<Comment>) get("commentList");
    }

    public void setCommentList(List<Comment> commentList) {
        put("commentList", commentList);
    }
    /*
    public List<Like> getLikeList() {
        return (List<Like>) get("likeList");
    }

    public void setCommentList(List<Like> likeList) {
        put("likeList", likeList);
    }
    */
}
