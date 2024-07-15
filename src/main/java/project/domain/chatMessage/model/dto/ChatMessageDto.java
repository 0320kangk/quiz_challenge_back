package project.domain.chatMessage.model.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageDto {

    @NotNull
    private String roomId;

    @NotNull
    private String writer;

    private String content;

    /*
    Responst (서버 -> 클라이언트) 로 전달할 때에는 @JsonFormat 을 사용,
    Request(클라이언트 -> 서버)로 전달할 때는 @DateTimeFormat 을 사용한다.
     */
}
