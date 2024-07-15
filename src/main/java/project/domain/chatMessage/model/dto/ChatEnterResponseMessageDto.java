package project.domain.chatMessage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatEnterResponseMessageDto {
    private String roomId;
    private String writer;
    private String content;
    private Set<String> participateNames;
    private String hostName;
}
