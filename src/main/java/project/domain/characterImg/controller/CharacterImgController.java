package project.domain.characterImg.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import project.domain.characterImg.service.CharacterImgService;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
public class CharacterImgController {

    private final CharacterImgService characterImgService;

    //이미자 바꾸기
    @PostMapping("/api/characterImg/change/{characterName}")
    public void characterImgChange(@PathVariable("characterName") String characterName, @AuthenticationPrincipal UserDetails userDetails) {
        characterImgService.changeCharacterImg(userDetails.getUsername(),characterName);
    }
    //이미지 가져오기
    @GetMapping("/api/characterImg")
    public Resource downloadCharacterImg(@AuthenticationPrincipal UserDetails userDetails) throws MalformedURLException {
        String memberCharacterImgFullPath = characterImgService.getMemberCharacterImgFullPath(userDetails.getUsername());
        return new UrlResource("file:" + memberCharacterImgFullPath);
    }


}
