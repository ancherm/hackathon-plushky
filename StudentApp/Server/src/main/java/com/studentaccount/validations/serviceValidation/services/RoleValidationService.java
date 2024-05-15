package com.studentaccount.validations.serviceValidation.services;

import com.studentaccount.domain.entities.UserRole;

public interface RoleValidationService {
    boolean isValid(UserRole role);
}
