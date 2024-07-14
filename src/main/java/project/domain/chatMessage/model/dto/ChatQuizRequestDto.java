package project.domain.chatMessage.model.dto;

import lombok.Getter;
import lombok.Setter;
import project.domain.chatGpt.model.enums.QuizType;

@Getter
@Setter
public class ChatQuizRequestDto {
    String roomId;
    QuizType quizType;
}
