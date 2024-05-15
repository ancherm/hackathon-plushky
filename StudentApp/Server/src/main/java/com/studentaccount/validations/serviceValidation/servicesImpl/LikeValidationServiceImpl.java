package com.studentaccount.validations.serviceValidation.servicesImpl;

import com.studentaccount.domain.entities.Like;
import com.studentaccount.validations.serviceValidation.services.LikeValidationService;
import org.springframework.stereotype.Component;

@Component
public class LikeValidationServiceImpl implements LikeValidationService {
    @Override
    public boolean isValid(Like like) {
        return like != null;
    }
}
