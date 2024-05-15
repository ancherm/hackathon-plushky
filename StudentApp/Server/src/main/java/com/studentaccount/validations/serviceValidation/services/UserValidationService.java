package com.studentaccount.validations.serviceValidation.services;

import com.studentaccount.domain.entities.User;
import com.studentaccount.domain.models.bindingModels.user.UserRegisterBindingModel;
import com.studentaccount.domain.models.bindingModels.user.UserUpdateBindingModel;
import com.studentaccount.domain.models.serviceModels.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserValidationService {
    boolean isValid(User user);

    boolean isValid(UserServiceModel userServiceModel);

    boolean isValid(UserRegisterBindingModel userRegisterBindingModel);

    boolean isValid(String firstParam, String secondParam);

    boolean isValid(UserUpdateBindingModel userUpdateBindingModel);

    boolean isValid(UserDetails userData);
}
