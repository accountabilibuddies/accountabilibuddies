package com.accountabilibuddies.accountabilibuddies.network;

import com.accountabilibuddies.accountabilibuddies.modal.Challenge;
import com.parse.ParseException;
import com.parse.SaveCallback;

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

    public void createChallange(Challenge challenge) {
        challenge.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    //Handle error and show and error message that challenge creation failed and try again
                    return;
                } else {
                    //Show success
                }
            }
        });
    }
}

