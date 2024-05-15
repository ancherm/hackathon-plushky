package com.studentaccount.validations.serviceValidation.servicesImpl;

import com.studentaccount.domain.entities.Like;
import com.studentaccount.domain.entities.Post;
import com.studentaccount.domain.entities.User;
import com.studentaccount.testUtils.LikesUtils;
import com.studentaccount.testUtils.PostsUtils;
import com.studentaccount.testUtils.UsersUtils;
import com.studentaccount.validations.serviceValidation.services.LikeValidationService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LikeValidationServiceTests {
    private LikeValidationService likeValidationService;

    @Before
    public void setupTest() {
        likeValidationService = new LikeValidationServiceImpl();
    }

    @Test
    public void isValid_whenValid_true() {
        User user = UsersUtils.createUser();
        Post post = PostsUtils.createPost(user, user);
        Like like = LikesUtils.createLike(user, post);

        boolean result = likeValidationService.isValid(like);
        assertTrue(result);
    }

    @Test
    public void isValid_whenNull_false() {
        Like like = null;
        boolean result = likeValidationService.isValid(like);
        assertFalse(result);
    }
}
