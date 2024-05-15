package com.studentaccount.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class ApplicationSocketSecurityConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {


    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .nullDestMatcher().authenticated()
                .simpDestMatchers("/app/**").hasAnyAuthority("ROOT", "ADMIN", "USER")
                .simpSubscribeDestMatchers("/topic/**","/queue/**", "/chat/**", "/user/**").hasAnyAuthority("ROOT", "ADMIN", "USER")
                .anyMessage().denyAll();
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
