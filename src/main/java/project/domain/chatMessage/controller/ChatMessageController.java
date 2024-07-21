package project.domain.chatMessage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import project.domain.chatGpt.model.dto.ChatCompletionResponseDto;
import project.domain.chatGpt.model.dto.ChatContent;
import project.domain.chatGpt.model.dto.QuestionRequestDto;
import project.domain.chatGpt.service.ChatGptChatCompletionService;
import project.domain.chatMessage.model.dto.*;
import project.domain.gameRoom.service.GameRoomService;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatMessageController {
    private final SimpMessagingTemplate messageTemplate;
    private final GameRoomService gameRoomService;
    private final ChatGptChatCompletionService chatGptChatCompletionServiceImpl;
    private static final int MAX_RETRIES = 3; // 최대 재요청 횟수    @MessageMapping(value = "/chat/room/quiz")
    @MessageMapping(value = "/chat/room/enter/{roomId}")
    public void enter(
            @DestinationVariable("roomId")  String roomId,
            @Payload ChatEnterRequestMessageDto enterMessage) {

        ChatEnterResponseMessageDto message = ChatEnterResponseMessageDto.builder()
                .roomId(roomId)
                .writer(enterMessage.getWriter())
                .content(enterMessage.getWriter() + "님이 채팅방에 참여하였습니다.")
                .participates(gameRoomService.getAllRoomParticipant(roomId))
                .hostName(gameRoomService.getHostName(roomId))
                .build();
        messageTemplate.convertAndSend("/subscribe/enter/room/" + message.getRoomId() , message);
    }
    @MessageMapping(value = "/chat/room/message/{roomId}")
    public void message(
            @DestinationVariable("roomId") String roomId,
            @Payload ChatMessageDto message ) {
        messageTemplate.convertAndSend("/subscribe/chat/room/" + roomId, message);
    }
    @MessageMapping(value = "/chat/room/status/{roomId}")
    public void statusMessage(
            @DestinationVariable("roomId") String roomId,
            @Payload ChatRoomStatusDto message) {
        log.info("publish room status {}", message);
        messageTemplate.convertAndSend("/subscribe/status/room/" + roomId , message);
    }
    @MessageMapping(value = "/chat/room/init/{roomId}")
    public void initMessage(
            @DestinationVariable("roomId") String roomId,
            @Payload String writer) {
        String initContent = "게임을 다시 시작합니다.";
        ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                .writer(writer)
                .content(initContent)
                .build();
        messageTemplate.convertAndSend("/subscribe/init/room/" + roomId, chatMessageDto);
    }
    @MessageMapping(value = "/chat/room/score/{roomId}")
    public void scoreMessage(@DestinationVariable("roomId") String roomId,
            @Payload ChatRoomUserScoreDto message) {
        log.info("publish room status {}", message);
        log.info("room Id {}", roomId);
        messageTemplate.convertAndSend("/subscribe/score/room/" + roomId , message);
    }

    @MessageMapping(value = "/chat/room/quiz/{roomId}")
    public void publishChatCompletionContent(
            @DestinationVariable("roomId") String roomId,
            @Payload ChatQuizRequestDto chatQuizRequestDto) throws JsonProcessingException {
        //문제를 요청해야함
        //QuestionRequestDto
        //roomid를 통해 room데이터 얻기
        log.info("roomId {}", roomId);
        QuestionRequestDto questionRequestDto = gameRoomService.getQuestionRequestDto(roomId, chatQuizRequestDto.getQuizType());

        chatGptChatCompletionServiceImpl.getChatCompletion(questionRequestDto);

        int retries = 0;
        List<ChatContent> chatContent = new ArrayList<>();
        while( chatContent.size() != questionRequestDto.getCount() && retries < MAX_RETRIES  ){
            try {
                ChatCompletionResponseDto chatCompletion = chatGptChatCompletionServiceImpl.getChatCompletion(questionRequestDto);
                chatContent = chatGptChatCompletionServiceImpl.getChatContent(chatCompletion);
            }catch (JsonProcessingException e){
                log.error("JSON 처리 중 오류 발생: {}", e.getMessage());
                retries++;
                continue; // 예외가 발생하면 재시도
            }
            retries++;
        }
        log.info("chatContents : {} ", chatContent);
        if(chatContent.size() != questionRequestDto.getCount() ) {
            String errorMessage = String.format("문제를 생성을 위해 %d 시도 했지만 실패했습니다.", MAX_RETRIES);
            log.info(" {}",errorMessage);
            return;
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
//        return ResponseEntity.ok(chatContent);
        messageTemplate.convertAndSend("/subscribe/quiz/room/" + roomId , chatContent);
    }
}
