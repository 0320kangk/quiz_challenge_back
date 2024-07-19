package project.domain.characterImg.entity;

import jakarta.persistence.*;
import project.domain.member.entity.Member;

@Entity
public class CharacterImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imgName;
    @OneToOne(mappedBy = "characterImg")
    private Member member;
}
