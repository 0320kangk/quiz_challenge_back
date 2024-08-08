package project.domain.chatGpt.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
public class ChatCompletionResponseDto {
    private String id;
    private String object;
    private long created;
    private List<Choice> choices;
    private String model;
    private Usage usage;
    private String system_fingerprint;

    @Getter
    @Setter
    @ToString
    public static class Choice {
        private int index;
        private Message message;
        private Object logprobs;
        private String finish_reason;

        // getter, setter 메소드들 생략
    }

    @Getter
    @Setter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {
        private String role;
        private String content; // 수정된 부분

        // getter, setter 메소드들 생략
    }


    @Getter
    @Setter
    @ToString
    public static class Usage {
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;

        // getter, setter 메소드들 생략
    }
}