package project.domain.gameRoom.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final GameRoomService gameRoomService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        MessageHeaderAccessor accessor = NativeMessageHeaderAccessor.getAccessor(event.getMessage(), SimpMessageHeaderAccessor.class);
        GenericMessage generic = (GenericMessage) accessor.getHeader("simpConnectMessage");
        Map<String, List<String>> nativeHeaders = (Map) generic.getHeaders().get("nativeHeaders");

        log.info("nativeHeaders data {}", nativeHeaders);
        String roomId =  nativeHeaders.get("roomId").get(0);
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        //잠만 websocket session id는 누군지를 알아채는거네,그런데지금 event를 통해 bear를얻을 수 있는거 아니였냐
        //?;;이것을 통해사용자 데이터를얻고

        log.info("test sessionId : {}", sessionId);
        log.info("test roomId : {}", roomId);

    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        //여기서 room idf
        log.info("[Disconnected] websocket session id : {}", sessionId);
    }
}
