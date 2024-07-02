package project.domain.member.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import project.domain.chat.chatMessage.model.domain.ChatMessage;
import project.domain.chat.chatRoom.model.domain.ChatRoom;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(nullable = true)
    private String password;

    @JsonIgnore
    private boolean activated;

    @Column(nullable = false)
    LocalDateTime createdDate;

    @ManyToMany
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

}
