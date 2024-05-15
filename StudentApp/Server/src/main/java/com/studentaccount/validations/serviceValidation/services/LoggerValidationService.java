package com.studentaccount.validations.serviceValidation.services;

import com.studentaccount.domain.models.serviceModels.LoggerServiceModel;

public interface LoggerValidationService {
    boolean isValid(LoggerServiceModel loggerServiceModel);

    boolean isValid(String method, String principal, String tableName, String action);

    boolean isValid(String username);
}
