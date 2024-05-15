package com.studentaccount.validations.serviceValidation.servicesImpl;

import com.studentaccount.domain.models.bindingModels.message.MessageCreateBindingModel;
import com.studentaccount.validations.serviceValidation.services.MessageValidationService;
import org.springframework.stereotype.Component;

@Component
public class MessageValidationServiceImpl implements MessageValidationService {

    @Override
    public boolean isValid(MessageCreateBindingModel messageCreateBindingModel) {
        return messageCreateBindingModel != null;
    }
}
