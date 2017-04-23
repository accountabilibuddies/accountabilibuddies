package com.accountabilibuddies.accountabilibuddies.util;

import android.content.res.Resources;

import com.accountabilibuddies.accountabilibuddies.model.Post;

import java.util.ArrayList;
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

        for(int i=0; i<posts.size();i++) {

            Post post = posts.get(i);
            if(i==0) {
                result.add(DateUtils.getDateFromDate(post.getCreatedAt()));
            } else {
                String currDate = DateUtils.getDateFromDate(post.getCreatedAt());
                String beforeDate = DateUtils.getDateFromDate(posts.get(i-1).getCreatedAt());
                if(!currDate.equals(beforeDate)) {
                    result.add(currDate);
                }
            }
            result.add(post);
        }
        result.add(Constants.LAST);

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
            posts.add(DateUtils.getDateFromDate(post.getCreatedAt()));
            posts.add(Constants.LAST);
        } else {
            int size = posts.size();
            String currDate = DateUtils.getDateFromDate(post.getCreatedAt());

            if(posts.get(size-1) instanceof Post) {
                Post lastPost = (Post)posts.get(size-1);
                String beforeDate = DateUtils.getDateFromDate(lastPost.getCreatedAt());
                if (!currDate.equals(beforeDate)) {
                    posts.add(currDate);
                }
            }
        }

        posts.add(posts.size()-1, post);
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
