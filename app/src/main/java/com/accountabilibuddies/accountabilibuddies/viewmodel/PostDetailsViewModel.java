package com.accountabilibuddies.accountabilibuddies.viewmodel;

import android.content.Context;
import android.widget.Toast;

import com.accountabilibuddies.accountabilibuddies.model.Post;
import com.accountabilibuddies.accountabilibuddies.network.APIClient;

public class PostDetailsViewModel {

    private Context context;
    private Post post;

    public PostDetailsViewModel(Context context, String postId) {

        this.context = context;

        getPostInfo(postId);
    }

    public void getPostInfo(String postId) {

        APIClient.getClient().getPostById(postId, new APIClient.GetPostListener() {

            @Override
            public void onSuccess(Post post) {

                PostDetailsViewModel.this.post = post;
            }

            @Override
            public void onFailure(String errorMessage) {
                String errorDescription = "Failed to get post " + postId;

                Toast.makeText(context, errorDescription, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
