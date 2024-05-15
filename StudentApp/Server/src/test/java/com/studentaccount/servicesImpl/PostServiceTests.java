package com.studentaccount.servicesImpl;

import com.studentaccount.domain.models.bindingModels.post.PostCreateBindingModel;
import com.studentaccount.repositories.PostRepository;
import com.studentaccount.repositories.UserRepository;
import com.studentaccount.services.PostService;
import com.studentaccount.testUtils.CommentsUtils;
import com.studentaccount.testUtils.PostsUtils;
import com.studentaccount.testUtils.UsersUtils;
import com.studentaccount.domain.entities.Comment;
import com.studentaccount.domain.entities.Post;
import com.studentaccount.domain.entities.User;
import com.studentaccount.domain.models.serviceModels.PostServiceModel;
import com.studentaccount.validations.serviceValidation.services.PostValidationService;
import com.studentaccount.validations.serviceValidation.services.UserValidationService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.studentaccount.utils.constants.ResponseMessageConstants.SERVER_ERROR_MESSAGE;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTests {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository mockPostRepository;

    @MockBean
    private UserRepository mockUserRepository;

    @MockBean
    private PostValidationService mockPostValidationService;

    @MockBean
    private UserValidationService mockUserValidationService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private List<Post> postList;

    @Before
    public void setUpTest() {
        postList = new ArrayList<>();
        when(mockPostRepository.findAllByTimelineUserIdOrderByTimeDesc("1"))
                .thenReturn(postList);
    }

    @Test
    public void getAllPosts_when2Posts_2Posts() {
        // Arrange
        List<User> users = UsersUtils.getUsers(2);
        List<Post> posts = PostsUtils.getPosts(2, users.get(0), users.get(1));

        List<Comment> comments = CommentsUtils.getComments(2, users.get(0), users.get(1), posts.get(0));
        posts.get(0).getCommentList().addAll(comments);
        posts.get(1).getCommentList().add(comments.get(1));
        posts.get(1).getCommentList().add(comments.get(0));
        posts.get(1).getCommentList().add(comments.get(0));

        postList.addAll(posts);

        // Act
        List<PostServiceModel> allPosts = postService.getAllPosts("1");

        // Assert
        Post expected = posts.get(0);
        PostServiceModel actual = allPosts.get(0);

        assertEquals(2, allPosts.size());
        assertEquals(expected.getContent(), actual.getContent());
        assertEquals(expected.getImageUrl(), actual.getImageUrl());
        assertEquals(expected.getTime(), actual.getTime());
        assertEquals(expected.getLoggedInUser().getId(), actual.getLoggedInUser().getId());

        verify(mockPostRepository).findAllByTimelineUserIdOrderByTimeDesc(any());
        verifyNoMoreInteractions(mockPostRepository);
    }

    @Test
    public void getAllPosts_whenNoPosts_returnEmptyPosts() {
        postList.clear();
        List<PostServiceModel> allPosts = postService.getAllPosts("1");

        assertTrue(allPosts.isEmpty());
    }

    @Test
    public void createPost_whenUsersAndPostCreateBindingModelAreValid_createPost() throws Exception {
        // Arrange
        PostCreateBindingModel postCreateBindingModel = PostsUtils.getPostCreateBindingModels(1).get(0);

        when(mockPostValidationService.isValid(any(PostCreateBindingModel.class)))
                .thenReturn(true);

        when(mockPostValidationService.isValid(any(Post.class)))
                .thenReturn(true);

        when(mockUserValidationService.isValid(any(User.class))).thenReturn(true);

        when(mockUserRepository.findById(any()))
                .thenReturn(java.util.Optional.of(new User()));


        // Act
        postService.createPost(postCreateBindingModel);

        // Assert
        verify(mockPostRepository).save(any());
        verifyNoMoreInteractions(mockPostRepository);
    }

    @Test(expected = Exception.class)
    public void createPost_whenUsersAreNotValid_throwException() throws Exception {
        // Arrange
        PostCreateBindingModel postCreateBindingModel = PostsUtils.getPostCreateBindingModels(1).get(0);

        when(mockPostValidationService.isValid(any(PostCreateBindingModel.class)))
                .thenReturn(true);

        when(mockUserRepository.findById(any()))
                .thenReturn(null);

        // Act
        postService.createPost(postCreateBindingModel);
    }

    @Test(expected = Exception.class)
    public void createPost_whenCreatePostBindingModelIsNotValid_throwException() throws Exception {
        // Arrange
        PostCreateBindingModel postCreateBindingModel = PostsUtils.getPostCreateBindingModels(1).get(0);

        when(mockPostValidationService.isValid(any(PostCreateBindingModel.class)))
                .thenReturn(false);

        // Act
        postService.createPost(postCreateBindingModel);
    }

    @Test(expected = Exception.class)
    public void createPost_whenUsersAndPostCreateBindingModelAreNotValid_throwException() throws Exception {
        // Arrange
        PostCreateBindingModel postCreateBindingModel = PostsUtils.getPostCreateBindingModels(1).get(0);

        when(mockPostValidationService.isValid(any(PostCreateBindingModel.class)))
                .thenReturn(false);

        when(mockUserValidationService.isValid(any(User.class))).thenReturn(false);

        when(mockUserRepository.findById(any()))
                .thenReturn(java.util.Optional.of(new User()));

        when(mockPostValidationService.isValid(any(Post.class)))
                .thenReturn(true);

        // Act
        postService.createPost(postCreateBindingModel);
    }

    @Test
    public void deletePost_whenUserAndPostAreValid_deletePost() throws Exception {
        // Arrange
        User user = UsersUtils.createUser();
        Post post = PostsUtils.createPost(user, user);

        when(mockUserRepository.findById(any()))
                .thenReturn(java.util.Optional.of(user));

        when(mockPostRepository.findById(any()))
                .thenReturn(java.util.Optional.of(post));

        when(mockPostValidationService.isValid(any(Post.class)))
                .thenReturn(true);

        when(mockUserValidationService.isValid(any(User.class))).thenReturn(true);

        // Act
        CompletableFuture<Boolean> result = postService.deletePost("1", "1");

        // Assert
        assertTrue(result.get());
    }

    @Test()
    public void deletePost_whenUserIsNotValid_throwException() throws Exception {
        // Arrange
        when(mockPostValidationService.isValid(any(Post.class)))
                .thenReturn(true);

        when(mockUserValidationService.isValid(any(User.class))).thenReturn(false);

        thrown.expect(Exception.class);
        thrown.expectMessage(SERVER_ERROR_MESSAGE);

        // Act
        CompletableFuture<Boolean> result = postService.deletePost("1", "1");

        // Assert
        assertNull(result.get());
    }

    @Test()
    public void deletePost_whenPostIsNotValid_throwException() throws Exception {
        // Arrange
        when(mockPostValidationService.isValid(any(Post.class)))
                .thenReturn(false);

        when(mockUserValidationService.isValid(any(User.class))).thenReturn(true);

        thrown.expect(Exception.class);
        thrown.expectMessage(SERVER_ERROR_MESSAGE);

        // Act
        CompletableFuture<Boolean> result = postService.deletePost("1", "1");

        // Assert
        assertNull(result.get());
    }

    @Test()
    public void deletePost_whenUserAndPostAreNotValid_throwException() throws Exception {
        // Arrange
        when(mockPostValidationService.isValid(any(Post.class)))
                .thenReturn(false);

        when(mockUserValidationService.isValid(any(User.class))).thenReturn(false);

        thrown.expect(Exception.class);
        thrown.expectMessage(SERVER_ERROR_MESSAGE);

        // Act
        CompletableFuture<Boolean> result = postService.deletePost("1", "1");

        // Assert
        assertNull(result.get());
    }

    @Test()
    public void deletePost_whenUserIsNotAuthorized_throwException() throws Exception {
        // Arrange
        List<User> users = UsersUtils.getUsers(2);
        Post post = PostsUtils.createPost(users.get(1), users.get(1));

        when(mockUserRepository.findById(any()))
                .thenReturn(java.util.Optional.of(users.get(0)));

        when(mockPostRepository.findById(any()))
                .thenReturn(java.util.Optional.of(post));

        when(mockPostValidationService.isValid(any(Post.class)))
                .thenReturn(true);

        when(mockUserValidationService.isValid(any(User.class))).thenReturn(true);

        thrown.expect(Exception.class);
        thrown.expectMessage(SERVER_ERROR_MESSAGE);

        // Act
        CompletableFuture<Boolean> result = postService.deletePost("5", "1");

        // Assert
        assertNull(result.get());
    }
}
