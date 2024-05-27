package project.domain.security.jwt.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class TokenDto {
    private String accessToken;
    private String refreshToken;
}