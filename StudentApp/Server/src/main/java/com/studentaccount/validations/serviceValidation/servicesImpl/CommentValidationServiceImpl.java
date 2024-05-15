package com.studentaccount.validations.serviceValidation.servicesImpl;

import com.studentaccount.domain.entities.Comment;
import com.studentaccount.domain.models.bindingModels.comment.CommentCreateBindingModel;
import com.studentaccount.validations.serviceValidation.services.CommentValidationService;
import org.springframework.stereotype.Component;

@Component
public class CommentValidationServiceImpl implements CommentValidationService {
    @Override
    public boolean isValid(Comment comment) {
        return comment != null;
    }

    @Override
    public boolean isValid(CommentCreateBindingModel commentCreateBindingModel) {
        return commentCreateBindingModel != null;
    }
}
