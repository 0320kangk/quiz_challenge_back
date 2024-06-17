package project.domain.chatGpt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ChatCompletionRequestDto {
    private String model;
    private float temperature;
    private Integer max_tokens;
    private Messages[] messages;

}
