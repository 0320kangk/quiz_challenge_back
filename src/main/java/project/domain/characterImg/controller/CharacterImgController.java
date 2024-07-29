package project.domain.characterImg.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import project.domain.characterImg.dto.CharacterImgDto;
import project.domain.characterImg.service.CharacterImgService;

import java.net.MalformedURLException;
import java.util.List;
@RestController
@RequiredArgsConstructor
public class CharacterImgController {
    private final CharacterImgService characterImgService;
    //이미자 바꾸기
    @PostMapping("/api/characterImg/change/{characterName}")
    public void ChangeCharacterImg(@PathVariable("characterName") String characterName, @AuthenticationPrincipal UserDetails userDetails) {
        characterImgService.changeCharacterImg(userDetails.getUsername(),characterName);
    }
    @GetMapping("/api/characterImg")
    public Resource downloadCharacterImg(@AuthenticationPrincipal UserDetails userDetails) throws MalformedURLException {
        String memberCharacterImgFullPath = characterImgService.getMemberCharacterImgFullPath(userDetails.getUsername());
        return new UrlResource("file:" + memberCharacterImgFullPath);
    }
    @GetMapping("/api/characterImg/all")
    public List<CharacterImgDto> downloadAllCharacterImg(){
        List<CharacterImgDto> allCharacterImgWithFullPath = characterImgService.getAllCharacter();
        return allCharacterImgWithFullPath;
    }

    @GetMapping("/api/characterImg/{characterImgName}")
    public ResponseEntity<?>  downloadAllCharacterImgByImgName(@PathVariable("characterImgName") String imgName) throws MalformedURLException {
        try{
            String characterImgFullPath = characterImgService.getCharacterImgFullPathByImgName(imgName);
            UrlResource urlResource = new UrlResource("file:" + characterImgFullPath);
            return ResponseEntity.ok().body(urlResource);
        }catch (IllegalArgumentException e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    @PostMapping("/api/characterImg/select/{characterName}")
    public  ResponseEntity<String> selectCharacterImg(@PathVariable("characterName") String characterName, @AuthenticationPrincipal UserDetails userDetails) {
        characterImgService.updateMemberCharacterImg (userDetails.getUsername(),characterName);
        return ResponseEntity.ok("캐릭터 선택 성공");
    }
}
