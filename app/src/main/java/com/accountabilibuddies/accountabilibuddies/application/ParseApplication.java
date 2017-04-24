package com.accountabilibuddies.accountabilibuddies.application;

import android.app.Application;
import android.content.Intent;

import com.accountabilibuddies.accountabilibuddies.R;
import com.accountabilibuddies.accountabilibuddies.activity.DrawerActivity;
import com.accountabilibuddies.accountabilibuddies.activity.LoginActivity;
import com.accountabilibuddies.accountabilibuddies.model.Challenge;
import com.accountabilibuddies.accountabilibuddies.model.Comment;
import com.accountabilibuddies.accountabilibuddies.model.Friend;
import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.interceptors.ParseLogInterceptor;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ParseApplication extends Application {

    private static ParseUser currentUser;

    @Override
    public void onCreate() {
        super.onCreate();

        // Use for troubleshooting -- remove this line for production
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        // Enable Local Datastore to fetch data offline
        //Parse.enableLocalDatastore(this);

        // Register all parse models here
        ParseObject.registerSubclass(Comment.class);
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Challenge.class);
        ParseObject.registerSubclass(Friend.class);

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
                .server("http://accountabilibuddies.herokuapp.com/parse").build());

        ParseFacebookUtils.initialize(getApplicationContext());

        setUpCrashlytics();
        setupFonts();
    }

    private void setupFonts() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    private void setUpCrashlytics() {

        Fabric.with(this, new Crashlytics());
    }

    public static void setCurrentUser(ParseUser user) {

        currentUser = user;
    }

    public static ParseUser getCurrentUser() {

        return currentUser;
    }

    public static void logOut() {

        currentUser = null;

        LoginManager.getInstance().logOut();
    }
}