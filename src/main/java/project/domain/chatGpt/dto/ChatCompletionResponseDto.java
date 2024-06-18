package project.domain.chatGpt.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatCompletionResponseDto {
    private String id;
    private String object;
    private long created;
    private Choice[] choices;
    private String model; // 추가: JSON 데이터의 model 필드와 매핑될 필드

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

    public static class Message {
        private String role;
        private String content;

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
