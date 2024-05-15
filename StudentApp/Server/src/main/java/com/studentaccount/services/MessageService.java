package com.studentaccount.services;

import com.studentaccount.domain.models.bindingModels.message.MessageCreateBindingModel;
import com.studentaccount.domain.models.viewModels.message.MessageFriendsViewModel;
import com.studentaccount.domain.models.serviceModels.MessageServiceModel;

import java.util.List;

public interface MessageService {

    MessageServiceModel createMessage(MessageCreateBindingModel messageCreateBindingModel, String loggedInUsername) throws Exception;

    List<MessageServiceModel> getAllMessages(String loggedInUsername, String chatUserId);

    List<MessageFriendsViewModel> getAllFriendMessages(String loggedInUsername);
}
