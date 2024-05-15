package com.studentaccount.services;

public interface LikeService {

    boolean addLike(String postId, String loggedInUserId) throws Exception;

    int getAllLikesForPost(String postId) throws Exception;
}
