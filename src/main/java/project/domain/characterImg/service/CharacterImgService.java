package project.domain.characterImg.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.domain.characterImg.entity.CharacterImg;
import project.domain.characterImg.repository.CharacterImgRepository;
import project.domain.member.entity.Member;
import project.domain.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class CharacterImgService {

    private final MemberRepository memberRepository;
    private final CharacterImgRepository characterImgRepository;
    @Value("${character-img-path}")
    private String characterImgPath;
    @Transactional
    public void changeCharacterImg(String memberEmail, String characterImgName) {
        Member member = memberRepository.findOneByEmail(memberEmail).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        CharacterImg characterImg = characterImgRepository.findOneByImgName(characterImgName).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이미지이름입니다."));
        member.setCharacterImg(characterImg);
        memberRepository.save(member);
    }

    public String getMemberCharacterImgFullPath(String memberEmail){
        String imgName = memberRepository.findOneCharacterImgNameByEmail(memberEmail).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        return characterImgPath +  imgName;
    }

}
