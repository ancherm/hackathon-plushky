package com.studentaccount.validations.serviceValidation.servicesImpl;

import com.studentaccount.domain.entities.UserRole;
import com.studentaccount.validations.serviceValidation.services.RoleValidationService;
import org.springframework.stereotype.Component;

@Component
public class RoleValidationServiceImpl implements RoleValidationService {
    @Override
    public boolean isValid(UserRole role) {
        return role != null;
    }
}
