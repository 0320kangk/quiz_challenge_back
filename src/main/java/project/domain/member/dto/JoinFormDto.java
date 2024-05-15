package project.domain.member.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // 기본 생성자 추가
@ToString
public class JoinFormDto {

    @NotNull
    @Size(min = 3, max = 50)
    String name;
    @NotNull
    String email;
    @NotNull
    String passwordOrigin;
    @NotNull
    String passwordCheck;
}
