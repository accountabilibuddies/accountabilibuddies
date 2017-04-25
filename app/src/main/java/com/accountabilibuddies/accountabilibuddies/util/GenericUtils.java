package com.accountabilibuddies.accountabilibuddies.util;

import android.content.res.Resources;

import com.accountabilibuddies.accountabilibuddies.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenericUtils {

    /**
     * Function adds the date in between the posts.
     * This should only be used in 1on1 timeline
     * @param posts
     * @return
     */
    public static List<Object> buildOneOnOnePosts(List<Post> posts) {

        List<Object> result = new ArrayList<>();
        String currDate, beforeDate;
        for(int i=0; i<posts.size();i++) {

            Post post = posts.get(i);
            if(i==0) {
                result.add("Challenge Started!");
            } else {
                currDate = DateUtils.getDateFromDate(post.getCreatedAt());
                beforeDate = DateUtils.getDateFromDate(posts.get(i-1).getCreatedAt());
                if(!currDate.equals(beforeDate)) {
                    result.add(beforeDate);
                }
            }
            result.add(post);
        }
        if(posts.size()>0) {
            result.add(DateUtils.getDateFromDate(posts.get(posts.size()-1).getCreatedAt()));
            result.add(Constants.FIRST);
        }

        Collections.reverse(result);

        return result;
    }

    /**
     * Function to add date when a post is inserted only if necessary.
     * This should only be used in 1on1 timeline
     * @param posts
     * @param post
     */
    public static void addPost(List<Object> posts, Post post) {

        if(posts.size()==0) {
            posts.add(Constants.FIRST);
            posts.add(DateUtils.getDateFromDate(post.getCreatedAt()));
            posts.add(post);
            posts.add("Challenge Started!");
        } else {
            String currDate = DateUtils.getDateFromDate(post.getCreatedAt());

            if(posts.get(2) instanceof Post) {
                Post lastPost = (Post)posts.get(2);
                String beforeDate = DateUtils.getDateFromDate(lastPost.getCreatedAt());
                if (!currDate.equals(beforeDate)) {
                    posts.add(1,currDate);
                }
                posts.add(2, post);
            }
        }
    }

    /**
     * Convert dp to px
     * @param dp
     * @return
     */
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
