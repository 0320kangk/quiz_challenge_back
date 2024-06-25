package project.domain.chatGpt.model.dto;


import lombok.*;
import project.domain.chatGpt.model.enums.QuizType;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatContent {
    private String question;
    private List<String> options;
    private String answer;
    private QuizType quizType;
}
