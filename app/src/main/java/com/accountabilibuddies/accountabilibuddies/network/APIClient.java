package com.accountabilibuddies.accountabilibuddies.network;

import android.graphics.Bitmap;
import android.util.Log;

import com.accountabilibuddies.accountabilibuddies.model.Challenge;
import com.accountabilibuddies.accountabilibuddies.model.Comment;
import com.accountabilibuddies.accountabilibuddies.model.Friend;
import com.accountabilibuddies.accountabilibuddies.model.Like;
import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.accountabilibuddies.accountabilibuddies.model.Scoreboard;
import com.accountabilibuddies.accountabilibuddies.util.CameraUtils;
import com.accountabilibuddies.accountabilibuddies.util.NetworkUtils;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Date;
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
    public interface ChallengeListener {
        void onSuccess();
        void onFailure(String error_message);
    }

    public interface GetChallengeListListener {
        void onSuccess(List<Challenge> challengeList);
        void onFailure(String error_message);
    }

    public interface PostListener {
        void onSuccess();
        void onFailure(String error_message);
    }

    public interface GetPostListener {
        void onSuccess(Post post);
        void onFailure(String errorMessage);
    }

    public interface GetChallengeListener {
        void onSuccess(Challenge challenge, List<Post> postList);
        void onFailure(String errorMessage);
    }

    public interface GetPostListListener {
        void onSuccess(List<Post> postList);
        void onFailure(String error_message);
    }

    public interface UploadFileListener {
        void onSuccess(String fileLocation);
        void onFailure(String error_message);
    }

    public interface CreateFriendListener {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public interface AddFriendToChallengeListener {
        void onSuccess(String status);
        void onFailure(String errorMessage);
    }

    public interface GetFriendsListener {
        void onSuccess(List<Friend> friends);
        void onFailure(String errorMessage);
    }

    public interface GetCommentsListListener {
        void onSuccess(List<Comment> commentsList);
        void onFailure(String error_message);
    }

    public interface UserFoundListener {
        void onSuccess(ParseUser user);
        void onFailure(String errorMessage);
    }

    public interface GetMembersListListener {
        void onSuccess(List<ParseUser> members);
        void onFailure(String error_message);
    }

    // Challenge API's
    public void createChallenge(Challenge challenge, ChallengeListener listener) {
        challenge.saveEventually (e -> {
            if (e != null) {
                listener.onFailure(e.getMessage());
            } else {
                listener.onSuccess();
            }
        });
    }

    public void getCompleteChallengeList(ParseUser user, GetChallengeListListener listener) {
        ParseQuery<Challenge> query = ParseQuery.getQuery(Challenge.class);

        if (!NetworkUtils.isOnline()) {
            query.fromLocalDatastore();
            query.fromPin("COMPLETED_CHALLENGES");
        }

        query.whereEqualTo("userList", user);
        query.whereLessThan("endDate", new Date());
        query.findInBackground((objects, e) -> {
            if (e != null) {
                listener.onFailure(e.getMessage());
            } else {
                listener.onSuccess(objects);
                ParseObject.pinAllInBackground("COMPLETED_CHALLENGES", objects);
            }
        });
    }

    public void getUpcomingChallengeList(ParseUser user, GetChallengeListListener listener) {
        ParseQuery<Challenge> query = ParseQuery.getQuery(Challenge.class);

        if (!NetworkUtils.isOnline()) {
            query.fromLocalDatastore();
            query.fromPin("UPCOMING_CHALLENGES");
        }

        query.whereEqualTo("userList", user);
        query.whereGreaterThan("startDate", new Date());
        query.findInBackground((objects, e) -> {
            if (e != null) {
                listener.onFailure(e.getMessage());
            } else {
                listener.onSuccess(objects);
                ParseObject.pinAllInBackground("UPCOMING_CHALLENGES", objects);
            }
        });
    }

    public void getCurrentChallengeList(ParseUser user, GetChallengeListListener listener) {
        ParseQuery<Challenge> query = ParseQuery.getQuery(Challenge.class);

        if (!NetworkUtils.isOnline()) {
            query.fromLocalDatastore();
            query.fromPin("CURRENT_CHALLENGES");
        }

        query.whereEqualTo("userList", user);
        query.whereLessThanOrEqualTo("startDate", new Date());
        query.whereGreaterThanOrEqualTo("endDate", new Date());
        query.findInBackground((objects, e) -> {
            if (e != null) {
                listener.onFailure(e.getMessage());
            } else {
                listener.onSuccess(objects);
                ParseObject.pinAllInBackground("CURRENT_CHALLENGES", objects);
            }
        });
    }

/*
    public void joinChallenge(String challengeObjectId, ChallengeListener listener) {
        ParseQuery<Challenge> query = ParseQuery.getQuery(Challenge.class);
        query.getInBackground(challengeObjectId, (object, e) -> {
            if (e == null) {
                object.add("userList", ParseApplication.getCurrentUser());
                object.saveEventually(e1 -> {
                    if (e1 != null) {
                        listener.onFailure(e1.getMessage());
                    } else {
                        listener.onSuccess();
                    }
                });
            } else {
                listener.onFailure(e.getMessage());
            }
        });
    }
*/
    public void deleteChallenge(String challengeObjectId, ChallengeListener listener) {
        ParseQuery<Challenge> query = ParseQuery.getQuery(Challenge.class);
        query.getInBackground(challengeObjectId, (object, e) -> {
            if (e == null) {
                object.deleteEventually(e1 -> {
                    if (e1 != null) {
                        listener.onFailure(e1.getMessage());
                    } else {
                        listener.onSuccess();
                    }
                });
            } else {
                listener.onFailure(e.getMessage());
            }
        });
    }

    public void getChallengeById(String challengeId, GetChallengeListener listener) {

        ParseQuery<Challenge> query = ParseQuery.getQuery(Challenge.class);

        if (!NetworkUtils.isOnline()) {
            query.fromLocalDatastore();
            query.fromPin(challengeId);
        }
        query.include("postList");
        query.getInBackground(
            challengeId,
            (Challenge challenge, ParseException e) -> {
                if (e == null) {
                    listener.onSuccess(challenge, challenge.getPostList());
                    challenge.pinInBackground(challengeId);
                } else {
                    listener.onFailure(e.getMessage());
                }

            }
        );
    }

    public void getMembersList(String challengeObjectId, GetMembersListListener listener) {
        ParseQuery<Challenge> query = ParseQuery.getQuery(Challenge.class);

        if (!NetworkUtils.isOnline()) {
            query.fromLocalDatastore();
            query.fromPin("members"+challengeObjectId);
        }

        query.include("userList");

        query.getInBackground(challengeObjectId, (object, e) -> {
            if (e == null) {
                listener.onSuccess(object.getUserList());
                object.pinInBackground("members"+challengeObjectId);
            } else {
                listener.onFailure(e.getMessage());
            }
        });

    }

    private void filterUser(List<ParseUser> users, ParseUser removeuser) {

        CollectionUtils.filter(
                users,
                (ParseUser user) -> {
                    String currentUserId = removeuser.getObjectId();
                    return !user.getObjectId().equals(currentUserId);
                }
        );
    }

    public void exitChallenge(String challengeObjectId, ChallengeListener listener) {
        ParseQuery<Challenge> query = ParseQuery.getQuery(Challenge.class);
        query.getInBackground(challengeObjectId, (challenge, e) -> {
            if (e == null) {
                List<ParseUser> users = challenge.getUserList();
                filterUser(users, ParseUser.getCurrentUser());
                challenge.put("userList", users);

                //challenge.addAllUnique("userList", users);
                challenge.saveInBackground(e1 -> {
                    if (e1 != null) {
                        listener.onFailure(e1.getMessage());
                    } else {
                        listener.onSuccess();
                    }
                });
            } else {
                listener.onFailure(e.getMessage());
            }
        });
    }

    public void createFriend(Friend friend, CreateFriendListener listener) {

        friend.saveInBackground(

                (ParseException e) -> {

                    if (e != null) {
                        listener.onFailure(e.getMessage());
                    } else {
                        listener.onSuccess();
                    }
                }
        );
    }

    public void addRemoveFriendToChallenge(String challengeId, ParseUser friend, AddFriendToChallengeListener listener) {
        ParseQuery<Challenge> query = ParseQuery.getQuery(Challenge.class);

        if (!NetworkUtils.isOnline()) {
            query.fromLocalDatastore();
            query.fromPin("members"+challengeId);
        }

        query.getInBackground(challengeId,
                (Challenge challenge, ParseException getException) -> {
            List<ParseUser> userList = challenge.getUserList();
            Boolean isScore = false;
            if (getException == null) {
                for(ParseUser user : userList) {
                    if(user.getObjectId().equals(friend.getObjectId())) {
                        filterUser(userList, friend);
                        challenge.put("userList", userList);
                        challenge.saveEventually(e -> {});
                        listener.onSuccess("remove");
                        //Not deleting scoreboard intentionally, preserving users previous score
                        return;
                    }
                }
                challenge.addUnique("userList", friend);

                for(Scoreboard sb : challenge.getScoreboard()) {
                    try {
                        sb.fetchIfNeeded();
                        if(sb.getUser().getObjectId().equals(friend.getObjectId())) {
                            isScore = true;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (!isScore) {
                    Scoreboard scoreboard = new Scoreboard(friend);
                    challenge.add("scoreboardList", scoreboard);
                }
                challenge.saveEventually((ParseException e) -> {});
                listener.onSuccess("add");
            } else {
                listener.onFailure(getException.getMessage());
            }
        });
    }

    private void rewardPointsForPost(String challengeId) {
        ParseQuery<Challenge> query = ParseQuery.getQuery(Challenge.class);

        query.getInBackground(challengeId, (Challenge challenge, ParseException getException) -> {
            List<Scoreboard> scoreboardList = challenge.getScoreboard();
            if (getException == null && scoreboardList != null) {
                for(Scoreboard sb : scoreboardList) {
                    try {
                        sb.fetchIfNeeded();
                        if(sb.getUser().getObjectId().equals(ParseUser.getCurrentUser().getObjectId())) {
                            sb.rewardPoints(10);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void getFriendsByUsername(String username, GetFriendsListener listener) {
        ParseQuery<Friend> query = ParseQuery.getQuery(Friend.class);
        query.whereEqualTo("username", username);

        if (!NetworkUtils.isOnline()) {
            query.fromLocalDatastore();
            query.fromPin("FRIENDS");
        }

        query.findInBackground(
                (List<Friend> friends, ParseException e) -> {
                    if (e != null) {
                        listener.onFailure(e.getMessage());
                    } else {
                        listener.onSuccess(friends);
                        ParseObject.pinAllInBackground("FRIENDS", friends);
                    }
                }
        );
    }

    public void getUser(String username, UserFoundListener listener) {
        ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        query.whereEqualTo("username", username);

        if (!NetworkUtils.isOnline()) {
            query.fromLocalDatastore();
            query.fromPin("USERS");
        }

        query.findInBackground((List<ParseUser> users, ParseException e) -> {
            if (e != null) {
                listener.onFailure(e.getMessage());
            } else {
                if (!users.isEmpty()) {
                    ParseObject.pinAllInBackground("USERS", users);
                    listener.onSuccess(users.get(0));
                }
            }
        });
    }

    //Post API's
    public void createPost(Post post, String challengeObjectId, PostListener listener) {
        post.saveInBackground(e -> {
            if (e != null) {
                listener.onFailure(e.getMessage());
            } else {
                //Add this post to the Challenge now
                ParseQuery<Challenge> query = ParseQuery.getQuery(Challenge.class);
                query.getInBackground(challengeObjectId, (object, e1) -> {

                    if (e1 == null) {
                        object.add("postList", post);

                        //TODO: Add progress here and move callback to common place
                        object.saveInBackground(e11 -> {
                            if (e11 != null) {
                                listener.onFailure(e11.getMessage());
                            } else {
                                listener.onSuccess();
                                rewardPointsForPost(challengeObjectId);
                            }
                        });
                    } else {
                        listener.onFailure(e1.getMessage());
                    }
                });
            }
        });
    }

    public void getPostList(String challengeObjectId, GetPostListListener listener) {
        ParseQuery<Challenge> query = ParseQuery.getQuery(Challenge.class);
        query.include("postList");

        query.getInBackground(challengeObjectId, (object, e) -> {
            if (e == null) {
                listener.onSuccess(object.getPostList());
            } else {
                listener.onFailure(e.getMessage());
            }
        });
    }

    public void getPostById(String postId, GetPostListener listener) {

        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);

        query.getInBackground(
                postId,
                (Post post, ParseException e) -> {
                    if (e == null) {
                        listener.onSuccess(post);
                    } else {
                        listener.onFailure(e.getMessage());
                    }

                }
        );
    }

    private void filterLikes(List<Like> likes) {
        CollectionUtils.filter(
                likes,
                (Like like) -> !like.getUser().getObjectId().equals(ParseUser.getCurrentUser().getObjectId())
        );
    }

    public void setLike(String postId, Boolean like) {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include("likeList");

        query.getInBackground(postId, (object, e) -> {
            if (e == null) {
                if (like) {
                    Like l = new Like();
                    l.setUser(ParseUser.getCurrentUser());
                    object.add("likeList", l);
                } else {
                    List<Like> likes = object.getLikeList();
                    filterLikes(likes);
                    object.put("likeList", likes);

                    object.saveEventually();
                }
            }
        });
    }

    public void getCommentList(String postObjectId, GetCommentsListListener listener) {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include("commentList");

        query.getInBackground(postObjectId, (object, e) -> {
            if (e == null) {
                listener.onSuccess(object.getCommentList());
            } else {
                listener.onFailure(e.getMessage());
            }
        });
    }

    public void addComment(String postId, Comment comment, PostListener listener) {
        comment.saveInBackground(e -> {
            if (e != null) {
                listener.onFailure(e.getMessage());
            } else {
                //Add this post to the Challenge now
                ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
                query.getInBackground(postId, (object, e1) -> {

                    if (e1 == null) {
                        object.add("commentList", comment);

                        object.saveInBackground(e11 -> {
                            if (e11 != null) {
                                listener.onFailure(e11.getMessage());
                            } else {
                                listener.onSuccess();
                            }
                        });
                    } else {
                        listener.onFailure(e1.getMessage());
                    }
                });
            }
        });
    }

    //Upload file
    public void uploadFile(String fileName, Bitmap bitmap, UploadFileListener listener) {
        byte[] bytes = CameraUtils.bitmapToByteArray(bitmap);
        final ParseFile photoFile = new ParseFile(fileName, bytes);
        photoFile.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e != null) {
                    listener.onFailure(e.getMessage());
                } else {
                    Log.d("Test", photoFile.getUrl());
                    listener.onSuccess(photoFile.getUrl());
                }
            }
        });
    }
}