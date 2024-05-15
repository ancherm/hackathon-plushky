package com.studentaccount.validations.serviceValidation.services;

import com.studentaccount.domain.entities.Comment;
import com.studentaccount.domain.models.bindingModels.comment.CommentCreateBindingModel;

public interface CommentValidationService {
    boolean isValid(Comment comment);

    boolean isValid(CommentCreateBindingModel commentCreateBindingModel);
}
