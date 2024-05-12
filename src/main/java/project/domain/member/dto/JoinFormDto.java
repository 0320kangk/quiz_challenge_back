package project.domain.member.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // 기본 생성자 추가
@ToString
public class JoinFormDto {

    String name;
    String email;
    String passwordOrigin;
    String passwordCheck;
}
