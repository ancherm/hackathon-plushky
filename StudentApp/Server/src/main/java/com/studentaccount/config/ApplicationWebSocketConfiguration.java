package com.studentaccount.config;

import io.jsonwebtoken.Jwts;
import com.studentaccount.domain.entities.User;
import com.studentaccount.services.UserService;
import com.studentaccount.utils.responseHandler.exceptions.CustomException;
import com.studentaccount.web.websocket.JWTAuthenticationToken;
import com.studentaccount.validations.serviceValidation.services.UserValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Optional;

import static com.studentaccount.utils.constants.ResponseMessageConstants.UNAUTHORIZED_SERVER_ERROR_MESSAGE;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class ApplicationWebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    private final UserService userService;
    private final UserValidationService userValidation;

    @Autowired
    public ApplicationWebSocketConfiguration(UserService userService, UserValidationService userValidation) {
        this.userService = userService;
        this.userValidation = userValidation;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app")
                .setUserDestinationPrefix("/user")
                .enableSimpleBroker("/chat", "/topic", "/queue");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    Optional.ofNullable(accessor.getNativeHeader("Authorization")).ifPresent(ah -> {
                        String bearerToken = ah.get(0).replace("Bearer ", "");
                        JWTAuthenticationToken token = getJWTAuthenticationToken(bearerToken);
                        accessor.setUser(token);
                    });
                }
                return message;
            }
        });
    }

    private JWTAuthenticationToken getJWTAuthenticationToken(String token) {
        if (token != null) {
            String username = Jwts.parser()
                    .setSigningKey("Secret".getBytes())
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody()
                    .getSubject();

            if (username != null) {
                UserDetails userData = this.userService
                        .loadUserByUsername(username);

                if (!userValidation.isValid(userData)) {
                    throw new CustomException(UNAUTHORIZED_SERVER_ERROR_MESSAGE);
                }

                JWTAuthenticationToken jwtAuthenticationToken =
                        new JWTAuthenticationToken(userData.getAuthorities(), token, (User) userData);

                jwtAuthenticationToken.setAuthenticated(true);

                return jwtAuthenticationToken;
            }
        }

        return null;
    }
}
