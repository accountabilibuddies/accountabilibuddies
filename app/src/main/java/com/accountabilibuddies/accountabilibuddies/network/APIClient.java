package com.accountabilibuddies.accountabilibuddies.network;

import com.accountabilibuddies.accountabilibuddies.modal.Challenge;
import com.accountabilibuddies.accountabilibuddies.modal.Post;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class APIClient {

    //Maintain an instance to reuse for all API calls
    private static APIClient client;

    private APIClient() {}

    public static APIClient getClient() {
        if (client == null) {
            client = new APIClient();
        }
        return client;
    }

    /**
     * Listener interface to send back data to fragments
     */
    public interface CreateChallengeListener {
        public void onSuccess();
        public void onFailure(String error_message);
    }

    public interface GetChallengeListListener {
        public void onSuccess(List<Challenge> challengeList);
        public void onFailure(String error_message);
    }

    public interface CreatePostListener {
        public void onSuccess();
        public void onFailure(String error_message);
    }

    public interface GetPostListListener {
        public void onSuccess(List<Post> postList);
        public void onFailure(String error_message);
    }

    // Challenge API's
    public void createChallange(Challenge challenge, CreateChallengeListener listener) {
        challenge.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    listener.onFailure(e.getMessage());
                } else {
                    listener.onSuccess();
                }
            }
        });
    }

    public void getChallengeList(ParseUser user, GetChallengeListListener listener) {
        ParseQuery<Challenge> query = ParseQuery.getQuery(Challenge.class);

        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.whereEqualTo("userList", user);
        query.findInBackground(new FindCallback<Challenge>() {
            @Override
            public void done(List<Challenge> objects, ParseException e) {
                if (e != null) {
                    listener.onFailure(e.getMessage());
                } else {
                    listener.onSuccess(objects);
                }
            }
        });
    }

    public void getChallengesByCategory(ArrayList<Integer> categories, GetChallengeListListener listener) {
        ParseQuery<Challenge> query = ParseQuery.getQuery(Challenge.class);

        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.whereContainedIn("category", categories);
        query.findInBackground(new FindCallback<Challenge>() {
            @Override
            public void done(List<Challenge> objects, ParseException e) {
                if (e != null) {
                    listener.onFailure(e.getMessage());
                } else {
                    listener.onSuccess(objects);
                }
            }
        });
    }

    public void joinChallenge() {

    }

    public void deleteChallenge() {

    }

    public void updateChallenge() {

    }

    public void exitChallenge() {

    }

    //Post API's
    public void createPost(Post post, String challengeObjectId, CreatePostListener listener) {
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    listener.onFailure(e.getMessage());
                } else {
                    //Add this post to the Challenge now
                    ParseQuery<Challenge> query = ParseQuery.getQuery(Challenge.class);
                    query.getInBackground(challengeObjectId, new GetCallback<Challenge>() {

                        public void done(Challenge object, ParseException e) {

                            if (e == null) {
                                object.add("postList", post);

                                //TODO: Add progress here and move callback to common place
                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e != null) {
                                            listener.onFailure(e.getMessage());
                                        } else {
                                            listener.onSuccess();
                                        }
                                    }
                                });
                            } else {
                                listener.onFailure(e.getMessage());
                            }
                        }
                    });
                }
            }
        });
    }

    public void getPostList(String challengeObjectId, GetPostListListener listener) {
        ParseQuery<Challenge> query = ParseQuery.getQuery(Challenge.class);

        //Get the challenge object and pass back the postList
        query.getInBackground(challengeObjectId, new GetCallback<Challenge>() {
            public void done(Challenge object, ParseException e) {
                if (e == null) {
                    //Cool the post is in the challenge now
                    listener.onSuccess(object.getPostList());
                } else {
                    listener.onFailure(e.getMessage());
                }
            }
        });
    }

    //Delete Post

    //Add Comment

    //Like/Unlike Post

}

