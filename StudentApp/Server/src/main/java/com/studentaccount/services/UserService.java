package com.studentaccount.services;

import com.studentaccount.domain.entities.User;
import com.studentaccount.domain.models.serviceModels.UserServiceModel;
import com.studentaccount.domain.models.viewModels.user.UserCreateViewModel;
import com.studentaccount.domain.models.viewModels.user.UserDetailsViewModel;
import com.studentaccount.domain.models.viewModels.user.UserEditViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserCreateViewModel createUser(UserServiceModel userRegisterBindingModel);

    boolean updateUser(UserServiceModel userUpdateBindingModel, String loggedInUserId) throws Exception;

    UserServiceModel updateUserOnlineStatus(String userName, boolean changeToOnline) throws Exception;

    UserDetailsViewModel getById(String id) throws Exception;

    UserEditViewModel editById(String id) throws Exception;

    User getByEmailValidation(String email);

    User getByUsernameValidation(String username);

    List<UserServiceModel> getAllUsers(String userId) throws Exception;

    boolean promoteUser(String id) throws Exception;

    boolean demoteUser(String id) throws Exception;

    boolean deleteUserById(String id) throws Exception;
}
