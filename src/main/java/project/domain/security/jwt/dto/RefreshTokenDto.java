package project.domain.security.jwt.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDto {
    String refreshToken;
}
