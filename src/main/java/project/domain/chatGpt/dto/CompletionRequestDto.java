package project.domain.chatGpt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CompletionRequestDto {
    private String model;

    private String prompt;

    private float temperature;
    private Integer max_tokens;

    @Builder
    CompletionRequestDto(String model, String prompt, Integer max_tokens, float temperature) {
        this.model = model;
        this.prompt = prompt;
        this.max_tokens = max_tokens;
        this.temperature = temperature;
    }
}
