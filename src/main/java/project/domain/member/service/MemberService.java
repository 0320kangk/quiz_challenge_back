package project.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.domain.member.dto.JoinFormDto;
import project.domain.member.entity.Authority;
import project.domain.member.entity.Member;
import project.domain.member.entity.enumerate.Role;
import project.domain.member.repository.MemberRepository;
import project.domain.member.service.util.SecurityUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public void joinMember(JoinFormDto joinFormDto){

        // 가입되어 있지 않은 회원이면,
        // 권한 정보 만들고
        Authority authority = Authority.builder()
                .authorityName(Role.GUEST.getKey())
                .build();

        Member member = Member.builder()
                .name(joinFormDto.getName())
                .email(joinFormDto.getEmail())
                .password(bCryptPasswordEncoder.encode(joinFormDto.getPasswordOrigin()))
                .authorities(Collections.singleton(authority))
                .createdDate(LocalDateTime.now())
                .build();

        memberRepository.save(member);
    }

    // 유저,권한 정보를 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<Member> getUserWithAuthorities(String username) {
        return memberRepository.findOneWithAuthoritiesByEmail(username);
    }

    // 현재 securityContext에 저장된 username의 정보만 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<Member> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(memberRepository::findOneWithAuthoritiesByEmail);
    }
}
