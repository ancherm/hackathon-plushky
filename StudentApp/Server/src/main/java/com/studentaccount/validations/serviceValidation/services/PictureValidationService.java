package com.studentaccount.validations.serviceValidation.services;

import com.studentaccount.domain.entities.Picture;

public interface PictureValidationService {
    boolean isValid(Picture picture);
}
