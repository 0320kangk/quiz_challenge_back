package project.domain.gameRoom.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import project.domain.security.jwt.TokenProvider;

import java.util.Objects;


@RequiredArgsConstructor
@Slf4j
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final TokenProvider tokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("test socket connect");
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if(StompCommand.CONNECT.equals(Objects.requireNonNull(accessor).getCommand())){
            String jwt = accessor.getFirstNativeHeader("Authorization");
            log.info("jwt : {}" ,jwt);
            if (StringUtils.hasText(jwt) && jwt.startsWith("Bearer ")) {
                jwt = jwt.substring(7);
            }
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                accessor.setUser(authentication);
            } else {
                log.info("유효한 JWT 토큰이 없습니다, uri: {}", accessor.getDestination());
                throw new IllegalArgumentException("유효한 JWT 토큰이 없습니다.");
            }
        }
        return message;
    }
}
