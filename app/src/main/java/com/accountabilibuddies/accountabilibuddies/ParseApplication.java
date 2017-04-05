package com.accountabilibuddies.accountabilibuddies;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.interceptors.ParseLogInterceptor;

import io.fabric.sdk.android.Fabric;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Use for troubleshooting -- remove this line for production
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

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
        /**
         * Uncomment this code for testing parse. You can go to mLab through Heroku to see the
         * data. TODO: Remove this after app is ready
         */
        /*
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
        */
    }

    private void setUpCrashlytics() {

        Fabric.with(this, new Crashlytics());
    }

}