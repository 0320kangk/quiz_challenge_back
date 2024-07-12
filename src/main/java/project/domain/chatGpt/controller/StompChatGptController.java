package project.domain.chatGpt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.domain.chatGpt.model.dto.ChatCompletionResponseDto;
import project.domain.chatGpt.model.dto.ChatContent;
import project.domain.chatGpt.model.dto.QuestionRequestDto;
import project.domain.chatGpt.service.ChatGptChatCompletionService;
import project.domain.gameRoom.model.dto.GameRoomSimpleResponseDto;
import project.domain.gameRoom.service.GameRoomService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StompChatGptController {
    
    private final ChatGptChatCompletionService chatGptChatCompletionServiceImpl;
    private final GameRoomService gameRoomService;
    private final SimpMessagingTemplate messageTemplate;
    private static final int MAX_RETRIES = 3; // 최대 재요청 횟수
    @MessageMapping(value = "/api/chat/room/quiz")
    public void publishChatCompletionContent(@Payload String roomId) throws JsonProcessingException {
        //문제를 요청해야함
        //QuestionRequestDto
        //roomid를 통해 room데이터 얻기
        log.info("roomId {}", roomId);
        QuestionRequestDto questionRequestDto = gameRoomService.getQuestionRequestDto(roomId);
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
