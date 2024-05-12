package project.domain.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import project.domain.member.dto.JoinFormDto;
import project.domain.member.entity.Member;
import project.domain.member.service.MemberService;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<?> joinMember(@RequestBody JoinFormDto joinFormDto) throws IOException {
        // RedirectView 생성 후 리다이렉트할 URL 설정
        log.info("joinFormDto: {}", joinFormDto);
        if(!joinFormDto.getPasswordOrigin().equals(joinFormDto.getPasswordCheck())){
            return ResponseEntity.badRequest().body("비밀번호가 다릅니다.");
        }
        memberService.joinMember(joinFormDto);

        return ResponseEntity.ok().build();
    }
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Member> getMyUserInfo() {
        return ResponseEntity.ok(memberService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Member> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(memberService.getUserWithAuthorities(username).get());
    }

  /*  @GetMapping("/api/test")
    public String TEST() {
        log.info("test: test");
        return "test:success";
    }*/
}
