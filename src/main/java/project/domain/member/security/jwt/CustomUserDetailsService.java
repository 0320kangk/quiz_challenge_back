package project.domain.member.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.domain.member.entity.Member;
import project.domain.member.entity.enumerate.Role;
import project.domain.member.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    @Transactional
    // 로그인시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 userdetails.User 객체를 생성해 리턴
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        log.info("loadUserBy test");
        return memberRepository.findOneWithAuthoritiesByEmail(email)
                .map(member-> createUser(email, member))
                .orElseThrow(() -> {
                    log.info("데이터베이스 찾기 실패");
                    return new UsernameNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다.");
                });
    }

    private User createUser(String memberName, Member member) {
        log.info("creatUser error");

        if (!member.isActivated()) {
            throw new RuntimeException(memberName + " -> 활성화되어 있지 않습니다.");
        }
        log.info("member : {}", member.getAuthorities().contains("ADMIN"));
        List<GrantedAuthority> grantedAuthorities = member.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(member.getEmail(),
                member.getPassword(),
                grantedAuthorities);
    }

}
