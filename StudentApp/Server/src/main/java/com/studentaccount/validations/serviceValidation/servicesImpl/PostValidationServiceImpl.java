package com.studentaccount.validations.serviceValidation.servicesImpl;

import com.studentaccount.domain.entities.Post;
import com.studentaccount.domain.models.bindingModels.post.PostCreateBindingModel;
import com.studentaccount.validations.serviceValidation.services.PostValidationService;
import org.springframework.stereotype.Component;

@Component
public class PostValidationServiceImpl implements PostValidationService {
    @Override
    public boolean isValid(Post post) {
        return post != null;
    }

    @Override
    public boolean isValid(PostCreateBindingModel postCreateBindingModel) {
        return postCreateBindingModel != null;
    }
}
