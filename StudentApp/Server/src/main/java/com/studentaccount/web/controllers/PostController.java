package com.studentaccount.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentaccount.domain.models.bindingModels.post.PostCreateBindingModel;
import com.studentaccount.services.PostService;
import com.studentaccount.utils.constants.ResponseMessageConstants;
import com.studentaccount.domain.models.serviceModels.PostServiceModel;
import com.studentaccount.domain.models.viewModels.post.PostAllViewModel;
import com.studentaccount.utils.responseHandler.exceptions.CustomException;
import com.studentaccount.utils.responseHandler.successResponse.SuccessResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController()
@RequestMapping(value = "/post")
public class PostController {

    private final PostService postService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    public PostController(PostService postService, ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.postService = postService;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    @PostMapping (value = "/create")
    public ResponseEntity<Object> createPost(@RequestBody @Valid PostCreateBindingModel postCreateBindingModel,
                                             Authentication principal) throws Exception {
        boolean post = this.postService.createPost(postCreateBindingModel);
        if (post) {
            SuccessResponse successResponse = new SuccessResponse(
                    LocalDateTime.now(),
                    ResponseMessageConstants.SUCCESSFUL_CREATE_POST_MESSAGE,
                    " ",
                    true
            );
            return new ResponseEntity<>(this.objectMapper.writeValueAsString(successResponse), HttpStatus.OK);
        }

        throw new CustomException(ResponseMessageConstants.SERVER_ERROR_MESSAGE);
    }

    @GetMapping(value = "/all/{id}")
    public List<PostAllViewModel> getAllPosts(@PathVariable(value = "id") String timelineUserId) {
        try {
            List<PostServiceModel> postServiceAllPosts = this.postService.getAllPosts(timelineUserId);

            return postServiceAllPosts.stream().map(postServiceModel -> {
                PostAllViewModel postAllViewModel = this.modelMapper.map(postServiceModel, PostAllViewModel.class);
                postAllViewModel.setLikeCount(postServiceModel.getLike().size());
                postAllViewModel.setPostId(postServiceModel.getId());
                return postAllViewModel;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomException(ResponseMessageConstants.SERVER_ERROR_MESSAGE);
        }
    }

    @PostMapping(value = "/remove")
    public ResponseEntity removePost(@RequestBody Map<String, Object> body) throws Exception {
        String loggedInUserId = (String) body.get("loggedInUserId");
        String postToRemoveId = (String) body.get("postToRemoveId");

        CompletableFuture<Boolean> result = this.postService.deletePost(loggedInUserId, postToRemoveId);

        if (result.get()) {
            SuccessResponse successResponse = new SuccessResponse(
                    LocalDateTime.now(),
                    ResponseMessageConstants.SUCCESSFUL_POST_DELETE_MESSAGE,
                    "",
                    true
            );
            return new ResponseEntity<>(this.objectMapper.writeValueAsString(successResponse), HttpStatus.OK);
        }

        throw new CustomException(ResponseMessageConstants.SERVER_ERROR_MESSAGE);
    }

}
