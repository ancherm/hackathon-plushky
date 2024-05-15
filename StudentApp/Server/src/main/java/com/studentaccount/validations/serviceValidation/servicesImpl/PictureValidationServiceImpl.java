package com.studentaccount.validations.serviceValidation.servicesImpl;

import com.studentaccount.domain.entities.Picture;
import com.studentaccount.validations.serviceValidation.services.PictureValidationService;
import org.springframework.stereotype.Component;

@Component
public class PictureValidationServiceImpl implements PictureValidationService {
    @Override
    public boolean isValid(Picture picture) {
        return picture != null;
    }
}
