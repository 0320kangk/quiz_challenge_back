package project.domain.characterImg.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.domain.member.entity.Member;

import java.util.List;

@Entity
@Getter
@Setter
public class CharacterImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imgName;
    @OneToMany(mappedBy = "characterImg")
    private List<Member> members;
}
