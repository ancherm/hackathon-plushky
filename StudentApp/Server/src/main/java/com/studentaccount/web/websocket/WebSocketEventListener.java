package com.studentaccount.web.websocket;

import com.studentaccount.services.UserService;
import com.studentaccount.domain.models.serviceModels.UserServiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {
    private final UserService userService;
    private final SimpMessagingTemplate template;

    @Autowired
    public WebSocketEventListener(UserService userService, SimpMessagingTemplate template) {
        this.userService = userService;
        this.template = template;
    }

    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) throws Exception {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String username = headers.getUser().getName();

        UserServiceModel userServiceModel = userService.updateUserOnlineStatus(username, true);
        String userId = userServiceModel.getId();
        WebSocketMessage message = new WebSocketMessage(WebSocketEventName.CONNECT, userId, username, true);

        template.convertAndSend(WebSocketEventName.CONNECT.getDestination(), message);
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) throws Exception {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String username = headers.getUser().getName();

        UserServiceModel userServiceModel = userService.updateUserOnlineStatus(username, false);
        String userId = userServiceModel.getId();
        WebSocketMessage message = new WebSocketMessage(WebSocketEventName.DISCONNECT, userId, username, false);

        template.convertAndSend(WebSocketEventName.DISCONNECT.getDestination(), message);
    }
}
