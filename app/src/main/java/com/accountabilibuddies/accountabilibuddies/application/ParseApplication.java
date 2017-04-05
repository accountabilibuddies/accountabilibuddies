package com.accountabilibuddies.accountabilibuddies.application;

import android.app.Application;

import com.accountabilibuddies.accountabilibuddies.modal.Challenge;
import com.accountabilibuddies.accountabilibuddies.modal.Comment;
import com.accountabilibuddies.accountabilibuddies.modal.Post;
import com.accountabilibuddies.accountabilibuddies.modal.User;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.interceptors.ParseLogInterceptor;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Use for troubleshooting -- remove this line for production
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        // Enable Local Datastore to fetch data offline
        //Parse.enableLocalDatastore(this);

        // Register all parse models here
        //ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Comment.class);
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Challenge.class);


        // Set applicationId, and server server based on the values in the Heroku settings.
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                // This corresponds to APP_ID env variable
                .applicationId("accountabilibuddiesCodePath")
                // set explicitly unless clientKey is explicitly configured on Parse server
                .clientKey(null)
                .addNetworkInterceptor(new ParseLogInterceptor())
                // Corresponds to the SERVER_URL
                .server("http://accountabilibuddies.herokuapp.com/parse/").build());
    }
}