package com.studentaccount.validations.serviceValidation.services;

import com.studentaccount.domain.entities.Like;

public interface LikeValidationService {
    boolean isValid(Like like);
}
