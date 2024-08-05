package project.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import project.domain.security.jwt.TokenProvider;
import project.domain.gameRoom.interceptor.JwtChannelInterceptor;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final TokenProvider tokenProvider;
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/subscribe"); // 서버에서 > 클라이언트 메시지 경로
        config.setApplicationDestinationPrefixes("/publish"); // 클라이언트에서 -> 서버 경로
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api/chat")
                .setAllowedOrigins("http://localhost:3000",
                        "https://localhost:3000",
                        "https://quizchallenge.site",
                        "http://quizchallenge.site",
                        "http://www.quizchallenge.site",
                        "https://www.quizchallenge.site")
                .addInterceptors()
                .withSockJS();//socket 연결 경로
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new JwtChannelInterceptor(tokenProvider));
    }
}
