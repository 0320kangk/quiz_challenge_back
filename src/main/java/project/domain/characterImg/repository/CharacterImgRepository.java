package project.domain.characterImg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.domain.characterImg.entity.CharacterImg;
import project.domain.member.entity.Member;

import java.util.Optional;

@Repository
public interface CharacterImgRepository extends JpaRepository<CharacterImg,Long > {
    Optional<CharacterImg> findOneByImgName (String imgName);

}
