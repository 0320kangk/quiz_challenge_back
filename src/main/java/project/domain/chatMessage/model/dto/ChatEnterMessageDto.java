package project.domain.chatMessage.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatEnterMessageDto {

    @NotNull
    private String roomId;

    @NotNull
    private String writer;
}
