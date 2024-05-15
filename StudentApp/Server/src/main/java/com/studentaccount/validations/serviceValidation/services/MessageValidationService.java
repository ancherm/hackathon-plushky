package com.studentaccount.validations.serviceValidation.services;

import com.studentaccount.domain.models.bindingModels.message.MessageCreateBindingModel;

public interface MessageValidationService {
    boolean isValid(MessageCreateBindingModel messageCreateBindingModel);
}
