package project.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findOneWithAuthoritiesByEmail (String email);
    @Query("SELECT m.name FROM Member m WHERE m.email = :email")
    Optional<String> findNameByEmail(@Param("email") String email);

    @Query("SELECT m FROM Member m " +
            "LEFT JOIN FETCH m.authorities " +
            "LEFT JOIN FETCH m.characterImg " +
            "WHERE m.email = :email")
    Optional<Member> findOneWithAuthoritiesAndCharacterImgByEmail(@Param("email") String email);

    Optional<Member> findOneByEmail (String email);

    @Query("SELECT m.characterImg.imgName FROM Member m WHERE m.email = :email")
    Optional<String> findOneCharacterImgNameByEmail(@Param("email") String email);


}
