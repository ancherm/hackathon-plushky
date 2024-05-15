package com.studentaccount.services;

import com.studentaccount.domain.models.bindingModels.post.PostCreateBindingModel;
import com.studentaccount.domain.models.serviceModels.PostServiceModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PostService {
    boolean createPost(PostCreateBindingModel postCreateBindingModel) throws Exception;

    List<PostServiceModel> getAllPosts(String timelineUserId);

    CompletableFuture<Boolean> deletePost(String loggedInUserId, String postToRemoveId) throws Exception;
}
