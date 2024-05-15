package project.domain.member.security.jwt.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class TokenDto {
    private String token;
}