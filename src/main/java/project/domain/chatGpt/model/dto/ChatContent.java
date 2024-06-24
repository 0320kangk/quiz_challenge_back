package project.domain.chatGpt.model.dto;


import lombok.*;

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
}
