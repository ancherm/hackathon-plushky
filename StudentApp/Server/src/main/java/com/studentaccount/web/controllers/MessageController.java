package com.studentaccount.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentaccount.domain.models.bindingModels.message.MessageCreateBindingModel;
import com.studentaccount.domain.models.viewModels.message.MessageAllViewModel;
import com.studentaccount.domain.models.viewModels.message.MessageFriendsViewModel;
import com.studentaccount.services.MessageService;
import com.studentaccount.utils.constants.ResponseMessageConstants;
import com.studentaccount.domain.models.serviceModels.MessageServiceModel;
import com.studentaccount.utils.responseHandler.exceptions.CustomException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping(value = "/message")
public class MessageController {
    private final SimpMessagingTemplate template;

    private final MessageService messageService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;


    @Autowired
    public MessageController(SimpMessagingTemplate template,
                             MessageService messageService,
                             ModelMapper modelMapper,
                             ObjectMapper objectMapper) {
        this.template = template;
        this.messageService = messageService;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = "/all/{id}")
    public List<MessageAllViewModel> getAllMessages(@PathVariable(value = "id") String chatUserId,
                                                    Authentication principal) {
        String loggedInUsername = principal.getName();

        List<MessageServiceModel> messageServiceModels = this.messageService.getAllMessages(loggedInUsername, chatUserId);

        return messageServiceModels.stream()
                .map(messageServiceModel -> modelMapper.map(messageServiceModel, MessageAllViewModel.class))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/friend")
    public List<MessageFriendsViewModel> getAllFriendMessages(Authentication principal) {
        String loggedInUsername = principal.getName();

        return this.messageService.getAllFriendMessages(loggedInUsername);
    }

    @MessageMapping("/message")
    public void createPrivateChatMessages(@RequestBody @Valid MessageCreateBindingModel messageCreateBindingModel,
                                          Principal principal,
                                          SimpMessageHeaderAccessor headerAccessor) throws Exception {
        MessageServiceModel message = this.messageService.createMessage(messageCreateBindingModel, principal.getName());
        MessageAllViewModel messageAllViewModel = this.modelMapper.map(message, MessageAllViewModel.class);

        if (messageAllViewModel != null) {
            String response = this.objectMapper.writeValueAsString(messageAllViewModel);
            template.convertAndSend("/user/" + message.getToUser().getUsername() + "/queue/position-update", response);
            template.convertAndSend("/user/" + message.getFromUser().getUsername() + "/queue/position-update", response);
            return;
        }
        throw new CustomException(ResponseMessageConstants.SERVER_ERROR_MESSAGE);
    }
}
