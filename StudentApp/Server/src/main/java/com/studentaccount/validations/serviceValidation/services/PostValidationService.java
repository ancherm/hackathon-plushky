package com.studentaccount.validations.serviceValidation.services;

import com.studentaccount.domain.entities.Post;
import com.studentaccount.domain.models.bindingModels.post.PostCreateBindingModel;

public interface PostValidationService {
    boolean isValid(Post post);

    boolean isValid(PostCreateBindingModel postCreateBindingModel);
}
