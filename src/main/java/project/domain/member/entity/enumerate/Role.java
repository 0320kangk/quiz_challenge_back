package project.domain.member.entity.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "손님");
    //    STOREHOST("ROLE_GUEST, ROLE_STORE_ADMIN", "가게 관리자");
    private final String key;
    private final String title;
}
